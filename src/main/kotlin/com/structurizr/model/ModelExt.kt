package com.structurizr.model

import cc.catalysts.structurizr.kotlin.ElementConfiguration
import com.anmi.c4.model.element.*
import com.structurizr.analysis.AbstractSpringComponentFinderStrategy.*

fun SoftwareSystem.assignTags(vararg tags: String): SoftwareSystem {
    this.addTags(*tags)
    return this
}

fun SoftwareSystem.assignTags(vararg tags: ETag): SoftwareSystem {
    this.addTags(*tags.map { it.name }.toTypedArray())
    return this
}

fun Relationship.assignTags(vararg tags: ETag): Relationship {
    this.addTags(*tags.map { it.name }.toTypedArray())
    return this
}

fun Model.getSystem(obj: ESoftwareSystem): SoftwareSystem {
    return this.getSoftwareSystemWithName(obj.label) ?: this.addSystem(obj)
}

fun Model.getPerson(obj: EPerson): Person {
    return this.getPersonWithName(obj.label) ?: this.addPerson(obj.label, obj.description)
}

fun Model.addSystem(system: ESoftwareSystem): SoftwareSystem {
    return this.getSoftwareSystemWithName(system.label) ?: addSoftwareSystem(system)
}

private fun Model.addSoftwareSystem(system: ESoftwareSystem): SoftwareSystem {
    val list: List<String> = system.tag.map { it.name }
    return this.addSoftwareSystem(system.location, system.label, system.description).assignTags(*list.toTypedArray())
}

fun SoftwareSystem.addContainer(container: EContainer): Container {
    return this.addContainer(container) {}
}

fun SoftwareSystem.addContainer(eContainer: EContainer, init: ElementConfiguration.() -> Unit): Container {
    val container = this.addContainer(eContainer.label, eContainer.description, eContainer.technologies.joinToString()).apply {
        addTags(ETag.E_CONTAINER_TAG.name)
    }
    val config: ElementConfiguration = ElementConfiguration().apply(init)
    config.tags.forEach { t -> container.addTags(t) }
    config.uses.forEach { d ->
        when (d.element) {
            is SoftwareSystem -> container.uses(d.element as SoftwareSystem, d.description, d.technology, d.interactionStyle)
            is Container -> container.uses(d.element as Container, d.description, d.technology, d.interactionStyle)
            is Component -> container.uses(d.element as Component, d.description, d.technology, d.interactionStyle)
        }
    }
    config.usedBy.forEach { d ->
        when (d.element) {
            is SoftwareSystem -> (d.element as SoftwareSystem).uses(container, d.description, d.technology, d.interactionStyle)
            is Container -> (d.element as Container).uses(container, d.description, d.technology, d.interactionStyle)
            is Component -> (d.element as Component).uses(container, d.description, d.technology, d.interactionStyle)
            is Person -> (d.element as Person).uses(container, d.description, d.technology, d.interactionStyle)
        }
    }
    return container
}

fun Model.tagSpringComponents() {
    this.softwareSystems.forEach {
        it.containers.forEach {
            it.components.filter { c -> c.technology == SPRING_REST_CONTROLLER }.forEach { c -> c.addTags(ETag.SPRING_REST_CONTROLLER.name) }
            it.components.filter { c -> c.technology == SPRING_SERVICE }.forEach { c -> c.addTags(ETag.SPRING_SERVICE.name) }
            it.components.filter { c -> c.technology == SPRING_REPOSITORY }.forEach { c -> c.addTags(ETag.SPRING_REPOSITORY.name) }
        }
    }
}

fun SoftwareSystem.getContainer(container: EContainer): Container {
    return this.getContainerWithName(container.label)!!
}


fun Model.findRelation(sourceCanonicalName: String, destinationCanonicalName: String): Relationship {
    val filter = this.relationships.filter {
        it.source.canonicalName == sourceCanonicalName && it.destination.canonicalName == destinationCanonicalName
    }
    require(filter.isNotEmpty()) { "Relationship with source $sourceCanonicalName and dest $destinationCanonicalName is not found" }
    return filter.first()
}

inline fun <reified T : Element> Model.findElementByCanonicalName(canonicalName: String): T? {
    val predicate: (Element) -> Boolean = { it.canonicalName == canonicalName }
    val foundComponents = this.components().filter(predicate)
    if (foundComponents.isNotEmpty()) {
        return firstFrom(foundComponents)
    }
    val foundContainers = this.softwareSystems.flatMap { s -> s.containers }.filter(predicate)
    if (foundContainers.isNotEmpty()) {
        return firstFrom(foundContainers)
    }
    val foundSystems = this.softwareSystems.filter(predicate)
    if (foundSystems.isNotEmpty()) {
        return firstFrom(foundSystems)
    }
    val foundPeople = this.people.filter(predicate)
    if (foundPeople.isNotEmpty()) {
        return firstFrom(foundPeople)
    }
    return null
}

inline fun <reified T : Element> firstFrom(foundComp: List<*>): T =
        foundComp.first() as T

fun Model.components(): List<Component> {
    return this.softwareSystems.flatMap { s -> s.containers }.flatMap { c -> c.components }
}

fun StaticStructureElement.uses(system: ESoftwareSystem, desc: String) {
    this.uses(model.getSystem(system), desc)
}

fun SoftwareSystem.usedBy(system: ESoftwareSystem, desc: String) {
    model.getSystem(system).uses(this, desc)
}

fun SoftwareSystem.usedBy(person: EPerson, desc: String) {
    model.getPerson(person).uses(this, desc)
}

package com.structurizr.model

import cc.catalysts.structurizr.kotlin.ElementConfiguration
import com.anmi.c4.model.element.*
import java.lang.reflect.Constructor

fun newModel(): Model {
    return try {
        val constructor: Constructor<*> = Model::class.java.getDeclaredConstructor()
        constructor.isAccessible = true
        constructor.newInstance() as Model
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

fun Model.getIdGenerator(): IdGenerator {
    val field = this::class.java.getDeclaredField("idGenerator")
    field.isAccessible = true
    return field.get(this) as IdGenerator
}

fun SoftwareSystem.assignTags(vararg tags: String): SoftwareSystem {
    this.addTags(*tags)
    return this
}

fun SoftwareSystem.assignTags(vararg tags: ITag): SoftwareSystem {
    this.addTags(*tags.map { it.label }.toTypedArray())
    return this
}

fun Relationship.assignTags(vararg tags: ITag): Relationship {
    this.addTags(*tags.map { it.label }.toTypedArray())
    return this
}

fun Model.getSystem(obj: ISystem): SoftwareSystem {
    return this.getSoftwareSystemWithName(obj.label) ?: this.addSystem(obj)
}

fun Model.getPerson(obj: IPerson): Person {
    return this.getPersonWithName(obj.label) ?: this.addPerson(obj.label, obj.description)
}

fun Model.addPerson(obj: IPerson): Person {
    return this.addPerson(obj.label, obj.description)
}

fun Model.addSystem(system: ISystem): SoftwareSystem {
    return this.getSoftwareSystemWithName(system.label) ?: addSoftwareSystem(system)
}

private fun Model.addSoftwareSystem(iSystem: ISystem): SoftwareSystem {
    val tags: List<String> = iSystem.tags.map { it.label }
    return this.addSoftwareSystem(iSystem.location, iSystem.label, iSystem.description).assignTags(*tags.toTypedArray())
}

fun SoftwareSystem.getContainer(iContainer: IContainer): Container {
    return this.getContainerWithName(iContainer.label)
        ?: this.addContainer(iContainer)
}

fun SoftwareSystem.addContainer(container: IContainer): Container {
    return this.addContainer(container) {}
}

fun SoftwareSystem.addContainer(iContainer: IContainer, init: ElementConfiguration.() -> Unit): Container {
    val container = this.addContainer(iContainer.label, iContainer.description, Technology.stringify(iContainer.technologies)).apply {
        assignTags(iContainer.tags)
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

private fun Element.assignTags(tags: Array<ITag>): Element {
    return this.apply { addTags(*tags.map { it.label }.toTypedArray()) }
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

fun StaticStructureElement.uses(system: ISystem, desc: String) {
    this.uses(model.getSystem(system), desc)
}

fun SoftwareSystem.usedBy(system: ISystem, desc: String) {
    model.getSystem(system).uses(this, desc)
}

fun SoftwareSystem.usedBy(person: IPerson, desc: String) {
    model.getPerson(person).uses(this, desc)
}

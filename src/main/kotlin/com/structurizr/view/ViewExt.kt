package com.structurizr.view

import com.anmi.c4.model.element.EPerson
import com.anmi.c4.model.element.ESoftwareSystem
import com.structurizr.model.*

internal fun ViewSet.addComponentViews(newModel: Model, remoteComponentViews: Collection<ComponentView>) {
    val thisViews = this.componentViews!!
    val nonExistingViews = filterByNonExistKey(remoteComponentViews, newModel, thisViews)
    thisViews.addAll(nonExistingViews)
    thisViews.forEach { v ->
        v.viewSet = this
        newModel.getSoftwareSystemWithName(v.softwareSystem.name)?.let { system ->
            v.softwareSystem = system
            v.container = system.getContainerWithName(v.container.name)
            v.elements.forEach { it.element = newModel.findElementByCanonicalName(it.element.canonicalName) }
            v.relationships.forEach { it.relationship = newModel.findRelation(it.relationship.source.canonicalName, it.relationship.destination.canonicalName) }
        }
    }
    this.setComponentViews(thisViews.toSet())
}

internal fun ViewSet.addDynamicViews(newModel: Model, remoteComponentViews: Collection<DynamicView>) {
    val thisViews = this.dynamicViews
    val nonExistingViews = filterByNonExistKey(remoteComponentViews, newModel, thisViews)
    thisViews.addAll(nonExistingViews)
    thisViews.forEach { v ->
        v.viewSet = this
        if (v.softwareSystem != null) {
            v.softwareSystem = newModel.getSoftwareSystemWithName(v.softwareSystem.name)
        }
        if (v.element != null) {
            v.element = newModel.findElementByCanonicalName(v.element.canonicalName)
        }
        v.elements.forEach { it.element = newModel.findElementByCanonicalName(it.element.canonicalName) }
        v.relationships.forEach { it.relationship = newModel.findRelation(it.relationship.source.canonicalName, it.relationship.destination.canonicalName) }
    }
    this.setDynamicViews(thisViews.toSet())
}

private inline fun <reified T : View> filterByNonExistKey(remoteComponentViews: Collection<T>, newModel: Model, thisViews: MutableCollection<out T>): Collection<T> {
    return remoteComponentViews.filter { view ->
        if (view.softwareSystem != null) {
            val system = newModel.getSoftwareSystemWithName(view.softwareSystem.name)
            !thisViews.map { it.key }.contains(view.key)
                    && system != null
                    && isContainerValid(view, system)
        } else {
            !thisViews.map { it.key }.contains(view.key)
        }
    }
}

private inline fun <reified T : View> isContainerValid(view: T, system: SoftwareSystem) =
        if (view is ComponentView) {
            system.getContainerWithName(view.container.name) != null
        } else true

fun View.add(element: Element) {
    this.addElement(element, true)
}

fun View.addElementsWithIncomingRelationTo(target: Element) {
    target.model.relationships.stream().filter { r -> r.destination == target }.forEach {
        this.add(it.source)
    }
}

fun View.addElementsWithOutgoingRelationFrom(source: Element) {
    source.relationships.forEach { relation ->
        this.add(source.model.getElement(relation.source.id)!!)
        this.add(source.model.getElement(relation.destination.id)!!)
    }
}

fun View.addAllElementsRelatedWith(element: Element) {
    this.addElementsWithIncomingRelationTo(element)
    this.addElementsWithOutgoingRelationFrom(element)
}

fun View.add(system: ESoftwareSystem) {
    this.add(this.model.getSystem(system))
}

fun View.add(person: EPerson) {
    this.add(this.model.getPerson(person))
}

fun DynamicView.add(source: Element, description: String, destination: Element, order: Int, suffix: String? = ""): RelationshipView {
    val relationshipView = add(source, description, destination)
    relationshipView.order = "$order-$suffix"
    return relationshipView
}

fun View.addElementsWithTag(elements: Set<Element>, tag: String) {
    elements.forEach {
        when {
            it.hasTag(tag) -> this.add(it)
        }
    }
}

fun View.removeElementsWithTagOnly(elements: Set<Element>, tag: String) {
    elements.forEach {
        when {
            it.hasTag(tag) && it.tagsAsSet.size == 1 -> this.removeElement(it)
        }
    }
}

fun View.removeRelationshipsNotConnectedToElements(vararg element: Element) {
    relationships.stream()
            .map { it.relationship }
            .filter { !element.contains(it.source) && !element.contains(it.destination) }
            .forEach(this::remove)
}
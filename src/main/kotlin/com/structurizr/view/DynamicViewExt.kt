package com.structurizr.view

import com.anmi.c4.model.element.IContainer
import com.anmi.c4.model.element.IPerson
import com.anmi.c4.model.element.ISystem
import com.structurizr.model.getContainer
import com.structurizr.model.getPerson
import com.structurizr.model.getSystem

fun DynamicView.add(container: IContainer, description: String, otherContainer: IContainer) {
    this.add(toContainer(container), description, toContainer(otherContainer))
}

fun DynamicView.add(container: IContainer, description: String, system: ISystem) {
    this.add(toContainer(container), description, model.getSystem(system))
}

fun DynamicView.add(system: ISystem, description: String, otherContainer: IContainer) {
    this.add(model.getSystem(system), description, toContainer(otherContainer))
}

fun DynamicView.add(person: IPerson, description: String, system: ISystem) {
    this.add(model.getPerson(person), description, model.getSystem(system))
}

fun DynamicView.add(person: IPerson, description: String, container: IContainer) {
    this.add(model.getPerson(person), description, toContainer(container))
}

private fun DynamicView.toContainer(container: IContainer) = model.getSystem(container.system).getContainer(container)
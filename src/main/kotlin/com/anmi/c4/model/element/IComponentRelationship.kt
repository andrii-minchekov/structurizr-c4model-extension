package com.anmi.c4.model.element

import com.structurizr.model.*

sealed class IComponentRelationship : (SoftwareSystem) -> Set<Component> {
    abstract override fun invoke(system: SoftwareSystem): Set<Component>

    class Component2Component(private val source: IComponent, private val target: IComponent, private val relDescription: String) :
        IComponentRelationship() {
        override fun invoke(system: SoftwareSystem): Set<Component> {
            val container = system.getContainer(source.container)
            val otherComponent = container.getComponent(target)
            return setOf(
                container.getComponent(source).apply {
                    uses(otherComponent, relDescription)
                },
                otherComponent
            )
        }
    }

    class Standalone(private val component: IComponent) : IComponentRelationship() {
        override fun invoke(system: SoftwareSystem): Set<Component> {
            return setOf(system.getContainer(component.container).addComponent(component))
        }
    }
}

package com.anmi.c4.model.element

import com.anmi.c4.analysis.ComponentFinderParams
import com.structurizr.model.Component
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.addComponentsFrom
import com.structurizr.model.getComponent
import com.structurizr.model.getContainer

sealed class IComponentRelationship : (SoftwareSystem) -> Set<Component> {
    abstract override fun invoke(system: SoftwareSystem): Set<Component>

    class Component2Component(private val source: IComponent, private val target: IComponent, private val relDescription: String) : IComponentRelationship() {
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

    class AutoScannedComponents(private val container: IContainer, private val criteria: ComponentFinderParams) : IComponentRelationship(){
        override fun invoke(system: SoftwareSystem): Set<Component> {
            return system.getContainer(container).addComponentsFrom(criteria)
        }
    }
}

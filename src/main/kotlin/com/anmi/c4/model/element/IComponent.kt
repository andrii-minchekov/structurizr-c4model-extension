package com.anmi.c4.model.element

import com.structurizr.model.Component
import com.structurizr.model.Container
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.getComponent
import com.structurizr.model.getContainer

interface IComponent {
    val container: IContainer
    val label: String
    val description: String
    val tags : Array<ITag>
    val url : String

    fun toModel(container: Container): Component = container.getComponent(this)

    fun uses(other: IComponent, relDescription: String): (SoftwareSystem) -> Set<Component> {
        return {
            val container = it.getContainer(container)
            val otherComponent = container.getComponent(other)
            setOf(
                    container.getComponent(this).apply {
                        uses(otherComponent, relDescription)
                    },
                    otherComponent
            )
        }
    }
}

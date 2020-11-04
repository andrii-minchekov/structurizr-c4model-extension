package com.anmi.c4.model.element

import com.structurizr.model.Container
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.getContainer
import com.structurizr.model.getSystem

interface IContainer: IElement {
    val system: ISystem
    val technologies: Array<Technology>

    fun uses(other: IContainer, relDescription: String, vararg technology: Technology): (SoftwareSystem) -> Set<Container> {
        return {
            val otherContainer = it.getContainer(other)
            setOf(
                    it.getContainer(this).apply {
                        uses(otherContainer, relDescription, Technology.stringify(technology))
                    },
                    otherContainer
            )
        }
    }

    fun uses(otherSystem: ISystem, relDescription: String, vararg technology: Technology): (SoftwareSystem) -> Set<Container> {
        return {
            setOf(
                    it.getContainer(this).apply {
                        it.uses(it.model.getSystem(otherSystem), relDescription, Technology.stringify(technology))
                        this.uses(it.model.getSystem(otherSystem), relDescription, Technology.stringify(technology))
                    })
        }
    }
}

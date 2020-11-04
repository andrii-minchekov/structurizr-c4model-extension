package com.anmi.c4.model.element

import com.structurizr.model.Container
import com.structurizr.model.Location
import com.structurizr.model.Model
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.getContainer
import com.structurizr.model.getSystem

interface ISystem : IElement {
    val location: Location
    val tag: Array<ITag>

    fun uses(otherSystem: ISystem, relDescription: String, vararg technology: Technology): (Model) -> SoftwareSystem {
        return {
            it.getSystem(this).apply {
                uses(it.getSystem(otherSystem), relDescription, Technology.stringify(technology))
            }
        }
    }

    fun uses(otherSystem: ISystem, otherContainer: IContainer, relDescription: String, vararg technology: Technology): (SoftwareSystem) -> Set<Container> {
        return {
            val container = it.model.getSystem(otherSystem).getContainer(otherContainer)
            it.model.getSystem(this).apply {
                uses(container, relDescription, Technology.stringify(technology))
            }
            setOf(container)
        }
    }

}
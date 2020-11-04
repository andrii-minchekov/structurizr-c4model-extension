package com.anmi.c4.model.element

import com.structurizr.model.Container
import com.structurizr.model.Model
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.getContainer
import com.structurizr.model.getPerson
import com.structurizr.model.getSystem

interface IPerson : IElement{

    fun uses(otherSystem: ISystem, relDescription: String, vararg technology: Technology): (Model) -> SoftwareSystem {
        return {
            it.getPerson(this).run {
                val system = it.getSystem(otherSystem)
                uses(system, relDescription, Technology.stringify(technology))
                system
            }
        }
    }

    fun uses(otherSystem: ISystem, otherContainer: IContainer, relDescription: String, vararg technology: Technology): (SoftwareSystem) -> Set<Container> {
        return {
            it.model.getPerson(this).run {
                val container = it.model.getSystem(otherSystem).getContainer(otherContainer)
                uses(container, relDescription, Technology.stringify(technology))
                setOf(container)
            }
        }
    }
}
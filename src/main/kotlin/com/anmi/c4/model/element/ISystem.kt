package com.anmi.c4.model.element

import com.structurizr.model.Location

interface ISystem : IElement {
    val location: Location

    fun uses(otherSystem: ISystem, relDescription: String, vararg technology: Technology): ISystemRelationship {
        return ISystemRelationship.System2System(this, otherSystem, relDescription, arrayOf(*technology))
    }

    fun uses(otherSystem: ISystem, otherContainer: IContainer, relDescription: String, vararg technology: Technology): IContainerRelationship {
        return IContainerRelationship.SystemToContainer(this, otherSystem, otherContainer, relDescription, arrayOf(*technology))
    }

}
package com.anmi.c4.model.element

import com.structurizr.model.Location

interface ISystem : IElement {
    val location: Location

    fun uses(otherSystem: ISystem, relDescription: String, vararg technology: Technology): ISystemRelationship {
        return ISystemRelationship.System2System(this, otherSystem, relDescription, arrayOf(*technology))
    }

    @Deprecated(
        message = "No reason to have ISystem as a parameter, use more short method from this interface",
        level = DeprecationLevel.WARNING,
        replaceWith = ReplaceWith("uses(otherContainer: IContainer, relDescription: String, vararg technology: Technology)")
    )
    fun uses(otherSystem: ISystem, otherContainer: IContainer, relDescription: String, vararg technology: Technology): IContainerRelationship {
        return IContainerRelationship.SystemToContainer(this, otherContainer, relDescription, arrayOf(*technology))
    }

    fun uses(otherContainer: IContainer, relDescription: String, vararg technology: Technology): IContainerRelationship {
        return IContainerRelationship.SystemToContainer(this, otherContainer, relDescription, arrayOf(*technology))
    }

}
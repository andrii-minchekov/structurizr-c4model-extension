package com.anmi.c4.model.element

interface IContainer: IElement {
    val system: ISystem
    val technologies: Array<Technology>

    fun uses(other: IContainer, relDescription: String, vararg technology: Technology): IContainerRelationship {
        return IContainerRelationship.Container2Container(this, other, relDescription, arrayOf(*technology))
    }

    fun uses(otherSystem: ISystem, relDescription: String, vararg technology: Technology): IContainerRelationship {
        return IContainerRelationship.Container2System(this, otherSystem, relDescription, arrayOf(*technology))
    }
}

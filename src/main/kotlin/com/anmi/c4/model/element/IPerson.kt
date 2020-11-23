package com.anmi.c4.model.element

interface IPerson : IElement {

    fun uses(otherSystem: ISystem, relDescription: String, vararg technology: Technology): ISystemRelationship {
        return ISystemRelationship.Person2System(this, otherSystem, relDescription, arrayOf(*technology))
    }

    fun uses(otherSystem: ISystem, otherContainer: IContainer, relDescription: String, vararg technology: Technology): IContainerRelationship {
        return IContainerRelationship.Person2Container(this, otherSystem, otherContainer, relDescription, arrayOf(*technology))
    }
}
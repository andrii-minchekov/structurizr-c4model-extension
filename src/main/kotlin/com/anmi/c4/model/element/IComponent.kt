package com.anmi.c4.model.element

import com.structurizr.model.*

interface IComponent : IElement{
    val container: IContainer
    val url : String

    fun toModel(container: Container): Component = container.getComponent(this)

    fun uses(other: IComponent, relDescription: String): IComponentRelationship {
        return IComponentRelationship.Component2Component(this, other, relDescription)
    }
}

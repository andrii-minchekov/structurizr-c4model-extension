package com.anmi.c4.model.element

interface IElement {
    val label: String
    val description: String
    val tags : Array<ITag>
}
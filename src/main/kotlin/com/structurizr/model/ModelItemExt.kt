package com.structurizr.model

import com.anmi.c4.model.element.ITag

fun ModelItem.addNonStyledTags(vararg tags: ITag) {
    val newTags = tags.joinToString(",") { it.label }
    this.setTags(newTags + "," + this.tags)
}
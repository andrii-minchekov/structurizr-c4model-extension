package com.structurizr.model

import com.anmi.c4.model.element.ITag

fun Component.assignTags(vararg tags: ITag): Component {
    return assignTags(*tags.map { it.name }.toTypedArray())
}

fun Component.assignTags(vararg tags: String): Component {
    this.addTags(*tags)
    return this
}

fun Component.assignUrl(url: String): Component {
    this.url = url
    return this
}
package com.structurizr.model

import cc.catalysts.structurizr.kotlin.ElementConfiguration
import cc.catalysts.structurizr.kotlin.addComponent
import com.anmi.c4.model.element.IComponent
import com.anmi.c4.model.element.ITag
import com.anmi.c4.model.element.Technology
import java.io.File

data class LocalPathToGitUrl(val gitRepoLocalDir: File, val gitRepoUrl: String, val gitBranch: String)

fun Container.getComponent(component: IComponent): Component {
    return this.addComponent(component)
}

fun Container.addComponent(component: IComponent): Component {
    return this.getComponentWithName(component.label) ?: this.addComponent(component.label, component.description).assignTags(*component.tags)
        .assignUrl(component.url)
}

fun Container.assignTags(vararg tags: ITag): Container {
    return assignTags(*tags.map { it.label }.toTypedArray())
}

fun Container.assignTags(vararg tags: String): Container {
    this.addTags(*tags)
    return this
}

fun Container.addNewComponent(name: String, description: String, vararg technologies: Technology, init: ElementConfiguration.() -> Unit): Component {
    return this.addComponent(name, description, technologies.joinToString { e -> e.toString() }) { init() }
}

internal fun Container.addComponent(remoteComponent: Component): Component {
    return this.addComponent(remoteComponent.name, remoteComponent.description, remoteComponent.technology).apply {
        tags = remoteComponent.tags
        perspectives = remoteComponent.perspectives
        url = remoteComponent.url
        technology = remoteComponent.technology
    }
}
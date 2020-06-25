package com.structurizr.model

import cc.catalysts.structurizr.kotlin.ElementConfiguration
import cc.catalysts.structurizr.kotlin.addComponent
import com.anmi.c4.analysis.ComponentFinderParams
import com.anmi.c4.analysis.LocalPathToGitUrl
import com.anmi.c4.model.element.ETag
import com.anmi.c4.model.element.Technology
import com.structurizr.analysis.ComponentFinder
import com.structurizr.analysis.SourceCodeComponentFinderStrategy
import java.io.File
import java.net.URI

fun Container.addComponentsFrom(params: ComponentFinderParams): Set<Component> {
    val strategies = mutableListOf(*params.strategies.toTypedArray())
    params.javaSources?.run {
        sourceDirs.forEach {
            strategies.add(SourceCodeComponentFinderStrategy(File(it), 150))
        }
    }
    val componentFinder = ComponentFinder(this, params.javaPackages.includes.iterator().next(), *strategies.toTypedArray())
    componentFinder.exclude(*params.javaPackages.excludes.map { it.pattern }.toTypedArray())
    params.javaPackages.includes.drop(1).forEach { componentFinder.addPackageName(it) }
    componentFinder.findComponents()
    params.javaSources?.localPathToGitUrl?.let {
        this.linkComponentsWithCode(params.javaSources.localPathToGitUrl)
    }
    return this.components
}

fun Container.linkComponentsWithCode(param: LocalPathToGitUrl) {
    this.components.forEach {
        for (codeElement in it.code) {
            val sourcePath = codeElement.url
            if (sourcePath != null) {
                codeElement.url = URI(sourcePath).path.replace(File(param.gitRepoLocalDir.toURI()).canonicalPath, "${param.gitRepoUrl}/tree/${param.gitBranch}")
                if (codeElement.type == it.type?.type) {
                    it.url = codeElement.url
                }
            }

        }
    }
}

fun Container.enrichComponentsFrom(components: Set<Component>) {
    this.components.forEach { thisComponent ->
        val found = components.firstOrNull { thisComponent.name.replace(" ", "") == it.name }
        if (found != null) {
            thisComponent.code = found.code
            thisComponent.size = found.size
            thisComponent.url = found.url
        }
    }
}
fun Container.assignTags(vararg tags: ETag): Container {
    return assignTags(*tags.map { it.name }.toTypedArray())
}
fun Container.assignTags(vararg tags: String): Container {
    this.addTags(*tags)
    return this
}

fun Container.addNewComponent(name: String, description: String, vararg technologies: Technology, init: ElementConfiguration.() -> Unit): Component {
    return this.addComponent(name, description, technologies.joinToString { e -> e.toString() }) { init() }
}

fun Container.addComponent(remoteComponent: Component): Component {
    return this.addComponent(remoteComponent.name, remoteComponent.description, remoteComponent.technology).apply {
        tags = remoteComponent.tags
        perspectives = remoteComponent.perspectives
        url = remoteComponent.url
                ?: remoteComponent.code.filter { it.name == remoteComponent.name }.map { it.url }.firstOrNull()
        technology = remoteComponent.technology
        code = remoteComponent.code
        remoteComponent.type?.type?.let {
            setType(it)
        }
        size = if (remoteComponent.size != 0L) {
            remoteComponent.size
        } else {
            remoteComponent.code.filter { it.name == remoteComponent.name }.map { it.size }.firstOrNull() ?: 0L
        }
    }
}
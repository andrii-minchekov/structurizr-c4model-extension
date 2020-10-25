package com.structurizr.model

import cc.catalysts.structurizr.kotlin.Dependency
import cc.catalysts.structurizr.kotlin.ElementConfiguration
import com.anmi.c4.EWorkspace
import com.anmi.c4.analysis.ComponentFinderParams
import com.anmi.c4.analysis.LocalPathToGitUrl
import com.anmi.c4.analysis.Packages
import com.anmi.c4.analysis.Sources
import com.anmi.c4.config.ConfigCreator
import com.anmi.c4.config.ConfigInstance
import com.anmi.c4.config.StructurizrFactory
import com.anmi.c4.diagram.ComponentDiagram.Companion.buildKey
import com.anmi.c4.model.element.IContainer
import com.anmi.c4.model.element.ITag
import com.anmi.c4.model.element.Technology
import com.anmi.c4.util.ScannedWorkspace
import com.structurizr.Workspace
import com.structurizr.documentation.replaceDocumentationBy
import com.structurizr.view.ComponentView
import com.structurizr.view.DynamicView
import com.structurizr.view.PaperSize
import com.structurizr.view.View
import com.structurizr.view.addComponentViews
import com.structurizr.view.addDynamicViews
import org.slf4j.LoggerFactory

fun Workspace.upload(containerName: String) {
    val workspaceId = this.id
    val config = ConfigCreator.fromId(workspaceId)
    val structurizrClient = StructurizrFactory.client(config)
    val remoteWorkspace = structurizrClient.getWorkspace(config.workspaceId)
    this.mergeInto(remoteWorkspace, containerName)
    if (structurizrClient.unlockWorkspace(workspaceId)) {
        LoggerFactory.getLogger(this::class.java).info("Workspace $workspaceId was unlocked")
    }
    structurizrClient.putWorkspace(config.workspaceId, this)
    LoggerFactory.getLogger(this::class.java).info("C4 Cloud workspace ${config.profileName} was updated successfully")
}

internal fun Workspace.mergeInto(remoteWorkspace: Workspace): Workspace {
    return mergeInto(remoteWorkspace, null, false)
}

fun Workspace.mergeInto(remoteWorkspace: Workspace, bypassContainer: IContainer): Workspace {
    return mergeInto(remoteWorkspace, bypassContainer.label)
}

fun Workspace.mergeInto(remoteWorkspace: Workspace, bypassContainerName: String): Workspace {
    return mergeInto(remoteWorkspace, bypassContainerName, true)
}

private fun Workspace.mergeInto(remoteWorkspace: Workspace, thisBypassContainer: String? = null, useRemoteDoc: Boolean = false): Workspace {
    remoteWorkspace.model.softwareSystems.forEach { remoteSystem ->
        val remoteContainers = excludeTargetIfPresent(remoteSystem, thisBypassContainer)
        addRemoteComponents(remoteContainers, remoteSystem)
    }
    this.model.updateRelationships(remoteWorkspace.model, thisBypassContainer)
    this.views.addComponentViews(this.model, remoteWorkspace.views.componentViews.filter {
        filterComponentViewOf(thisBypassContainer, it)
    })
    this.views.addDynamicViews(this.model, remoteWorkspace.views.dynamicViews.filter {
        filterComponentViewOf(thisBypassContainer, it)
    })
    if (useRemoteDoc) {
        this.replaceDocumentationBy(remoteWorkspace.documentation)
    } else {
        this.importDecisionsFrom(remoteWorkspace)
    }
    this.model.tagSpringComponents()
    return this
}

private fun Workspace.importDecisionsFrom(workspace: Workspace) {
    workspace.documentation.decisions.forEach {
        this.documentation.addDecision(
                it.element?.let { exist: Element -> this.model.getSoftwareSystemWithName(exist.name) }, it.id, it.date, it.title, it.status, it.format, it.content
        )
    }
}

private inline fun <reified T : View> filterComponentViewOf(targetContainer: String?, view: T): Boolean {
    return if (targetContainer != null) {
        val viewScopeElement = when (view) {
            is ComponentView -> view.container ?: view.model.getElement(view.containerId)
            ?: throw IllegalStateException("either container or containerId should be present in ComponentView")
            is DynamicView -> view.element ?: view.model.getElement(view.elementId)
            ?: throw IllegalStateException("either element or elementId should be present in DynamicView")
            else -> throw IllegalArgumentException("Only Component or Dynamic Component View is supported")
        }
        viewScopeElement.name != (targetContainer)
    } else true
}

private fun Workspace.addRemoteComponents(remoteContainers: List<Container>, s: SoftwareSystem) {
    remoteContainers.forEach { c ->
        if (c.components.isNotEmpty()) {
            this.model.getSoftwareSystemWithName(s.name)?.let { thisSystem ->
                thisSystem.getContainerWithName(c.name)?.let { thisContainer: Container ->
                    c.components.forEach { if (thisContainer.getComponentWithName(it.name) == null) thisContainer.addComponent(it) }
                }
            }
        }
    }
}

private fun excludeTargetIfPresent(s: SoftwareSystem, targetContainer: String?): List<Container> {
    return s.containers.filter {
        if (targetContainer != null) {
            it.name != targetContainer
        } else true
    }
}

private fun Model.updateRelationships(remoteModel: Model, thisBypassContainer: String?) {
    remoteModel.elements.forEach { element ->
        this.findElementByCanonicalName<Element>(element.canonicalName)?.let {
            val relationships = it.relationships
            val componentRelations = element.relationships.filter(isComponentRelationsNotRelatedToBypassContainer(this, thisBypassContainer))
            relationships.addAll(componentRelations)
            it.relationships = relationships
        }
    }
    this.elements.forEach { element ->
        element.relationships.filter(isComponentRelationsNotRelatedToBypassContainer(this, thisBypassContainer)).forEach { r ->
            if (r.destination is Component || r.source is Component) {
                r.id = EWorkspace.GENERATOR.generateId(r)

                remoteModel.getElement(r.sourceId)?.let {
                    r.source = this.findElementByCanonicalName(it.canonicalName)
                    r.sourceId = r.source.id
                }

                remoteModel.getElement(r.destinationId)?.let {
                    r.destination = this.findElementByCanonicalName(it.canonicalName)
                    r.destinationId = r.destination.id
                }
                this.addRelationshipInternal(r)
            }
        }
    }
}

private fun isComponentRelationsNotRelatedToBypassContainer(model: Model, bypassContainer: String?): (Relationship) -> Boolean {
    if (bypassContainer == null) {
        return { isComponentAndValid(it.destination, model) || isComponentAndValid(it.source, model) }
    }
    return {
        isComponentAndValid(it.destination, model, bypassContainer) || isComponentAndValid(it.source, model, bypassContainer)
    }
}

private fun isComponentAndValid(element: Element, model: Model, bypassContainer: String? = null): Boolean {
    if (element is Component) {
        model.findElementByCanonicalName<Component>(element.canonicalName)?.let { component ->
            model.findElementByCanonicalName<Container>(component.container.canonicalName)?.let { container ->
                if (model.findElementByCanonicalName<SoftwareSystem>(container.softwareSystem.canonicalName) != null) {
                    if (bypassContainer == null) {
                        return true
                    }
                    return bypassContainer != container.name
                }
            }
        }
    }
    return false
}

private fun Model.addRelationshipInternal(r: Relationship) {
    val clazz = Model::class.java
    val declaredMethod = clazz.getDeclaredMethod("addRelationshipToInternalStructures", Relationship::class.java)
    declaredMethod.isAccessible = true
    declaredMethod.invoke(this, r)
}

fun Workspace.defaultComponentView(container: Container): ComponentView {
    val view = this.views.createComponentView(container, buildKey(container.softwareSystem, container), "Default Component view of ${container.name}")
    view.paperSize = PaperSize.A2_Landscape
    view.addAllComponents()
    return view
}

fun ElementConfiguration.uses(element: Element, description: String, vararg technologies: Technology, interactionStyle: InteractionStyle? = null) {
    uses.add(Dependency(element, description, technologies.joinToString(transform = Technology::toString), interactionStyle))
}

fun ElementConfiguration.withTags(vararg tag: ITag) {
    this.tags.addAll(tag.map { it.name })
}

fun ElementConfiguration.usedBy(element: Element, description: String, vararg technologies: Technology, interactionStyle: InteractionStyle? = null) {
    this.usedBy.add(Dependency(element, description, technologies.joinToString(transform = Technology::toString), interactionStyle))
}

fun Workspace.scanComponentsWith(config: ConfigInstance, containerName: String = "Scratch-${(0..10).random()}", packagesWithComponents: Set<String>, javadocSourceDirs: Set<String>? = Sources().sourceDirs,
                                 localPathToGitUrl: LocalPathToGitUrl? = null) {
    val container = this.model.getSystem(ScannedWorkspace.fakeSystem()).addContainer(containerName, "It's just a playground container", "")
    container.addComponentsFrom(ComponentFinderParams(Packages(packagesWithComponents), javadocSourceDirs?.let { Sources(javadocSourceDirs, localPathToGitUrl) }))
    this.defaultComponentView(container)
    StructurizrFactory.client(config).putWorkspace(config.workspaceId, this)
}
package com.anmi.c4.diagram

import com.anmi.c4.model.element.*
import com.anmi.c4.util.refine
import com.structurizr.Workspace
import com.structurizr.model.getSystem
import com.structurizr.view.*

interface ContainerDiagram : Diagram<ContainerView> {

    val targetSystem: ISystem

    override val key: String
        get() = "${targetSystem.label}_${type.name}".refine()

    override val type: DiagramType
        get() = DiagramType.CONTAINER

    override fun draw(workspace: Workspace, vararg tag: ITag): ContainerView {
        val system = workspace.model.getSystem(targetSystem)
        val containerView = workspace.views.createContainerView(system, key, "The container diagram for the ${targetSystem.label} System")
        if (autoLayoutEnabled) containerView.enableAutomaticLayout()
        system.containers.forEach(containerView::addAllElementsRelatedWith)
        return containerView
    }
}

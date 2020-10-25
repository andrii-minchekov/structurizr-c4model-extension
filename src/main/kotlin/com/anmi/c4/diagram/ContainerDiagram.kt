package com.anmi.c4.diagram

import com.anmi.c4.model.element.ITag
import com.anmi.c4.util.refine
import com.structurizr.Workspace
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.ContainerView
import com.structurizr.view.addAllElementsRelatedWith

interface ContainerDiagram : Diagram<ContainerView> {

    val targetSystem: SoftwareSystem

    override val key: String
        get() = "${targetSystem.name}_${type.name}".refine()

    override val type: DiagramType
        get() = DiagramType.CONTAINER

    override fun draw(workspace: Workspace, vararg tag: ITag): ContainerView {
        val containerView = workspace.views.createContainerView(targetSystem, key, "The container diagram for the ${targetSystem.name} System")
        containerView.enableAutomaticLayout()
        targetSystem.containers.forEach(containerView::addAllElementsRelatedWith)
        return containerView
    }
}

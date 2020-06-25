package com.anmi.c4.model

import com.anmi.c4.diagram.ContainerDiagram
import com.structurizr.Workspace
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.ContainerView
import com.structurizr.view.PaperSize
import com.structurizr.view.addAllElementsRelatedWith

class SomeContainerDiagram(val model: SomeSystemModel) : ContainerDiagram {

    override val targetSystem: SoftwareSystem
        get() = model.system

    override fun draw(workspace: Workspace): ContainerView {
        val containerView = workspace.views.createContainerView(targetSystem, key, "The container diagram for the ${targetSystem.name} System")
        containerView.paperSize = PaperSize.A4_Landscape
        targetSystem.containers.forEach(containerView::addAllElementsRelatedWith)
        containerView.removeRelationshipsNotConnectedToElement(model.container)
        return containerView
    }
}
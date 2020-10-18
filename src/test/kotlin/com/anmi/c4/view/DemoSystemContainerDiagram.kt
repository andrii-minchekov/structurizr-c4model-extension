package com.anmi.c4.view

import com.anmi.c4.diagram.ContainerDiagram
import com.anmi.c4.model.EContainerInstance
import com.structurizr.Workspace
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.getContainer
import com.structurizr.view.ContainerView
import com.structurizr.view.PaperSize
import com.structurizr.view.addAllElementsRelatedWith

class DemoSystemContainerDiagram(override val targetSystem: SoftwareSystem) : ContainerDiagram {
    override fun draw(workspace: Workspace): ContainerView {
        val containerView = workspace.views.createContainerView(targetSystem, key, "The container diagram for the ${targetSystem.name} System")
        containerView.paperSize = PaperSize.A4_Landscape
        containerView.enableAutomaticLayout()
        targetSystem.containers.forEach(containerView::addAllElementsRelatedWith)
        containerView.removeRelationshipsNotConnectedToElement(targetSystem.getContainer(EContainerInstance.ORDER_SERVICE))
        return containerView
    }
}
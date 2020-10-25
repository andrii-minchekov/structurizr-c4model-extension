package com.anmi.c4.demosystem.view

import com.anmi.c4.demosystem.model.EContainer
import com.anmi.c4.diagram.ContainerDiagram
import com.anmi.c4.model.element.ITag
import com.structurizr.Workspace
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.getContainer
import com.structurizr.view.ContainerView
import com.structurizr.view.PaperSize
import com.structurizr.view.addAllElementsRelatedWith

class DemoSystemContainerDiagram(override val targetSystem: SoftwareSystem) : ContainerDiagram {
    override fun draw(workspace: Workspace, vararg tag: ITag): ContainerView {
        val containerView = workspace.views.createContainerView(targetSystem, key, "The container diagram for the ${targetSystem.name} System")
        containerView.paperSize = PaperSize.A4_Landscape
        containerView.enableAutomaticLayout()
        targetSystem.containers.forEach(containerView::addAllElementsRelatedWith)
        containerView.removeRelationshipsNotConnectedToElement(targetSystem.getContainer(EContainer.ORDER_SERVICE))
        return containerView
    }
}
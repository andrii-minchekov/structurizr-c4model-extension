package com.anmi.c4.demosystem.view

import com.anmi.c4.demosystem.model.EContainer
import com.anmi.c4.diagram.ContainerDiagram
import com.anmi.c4.model.element.ISystem
import com.anmi.c4.model.element.ITag
import com.structurizr.Workspace
import com.structurizr.model.getContainer
import com.structurizr.model.getSystem
import com.structurizr.view.ContainerView
import com.structurizr.view.PaperSize
import com.structurizr.view.addAllElementsRelatedWith

class DemoSystemContainerDiagram(override val targetSystem: ISystem) : ContainerDiagram {
    override fun draw(workspace: Workspace, vararg tag: ITag): ContainerView {
        val system = workspace.model.getSystem(targetSystem)
        val containerView = workspace.views.createContainerView(system, key, "The container diagram for the ${targetSystem.label} System")
        containerView.paperSize = PaperSize.A4_Landscape
        containerView.enableAutomaticLayout()
        system.containers.forEach(containerView::addAllElementsRelatedWith)
        containerView.removeRelationshipsNotConnectedToElement(system.getContainer(EContainer.ORDER_SERVICE))
        return containerView
    }
}
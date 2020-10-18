package com.anmi.c4.view

import com.anmi.c4.diagram.ComponentDiagram
import com.anmi.c4.model.EContainerInstance
import com.structurizr.Workspace
import com.structurizr.model.Container
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.getContainer
import com.structurizr.view.ComponentView

class OrderServiceComponentDiagram(override val targetSystem: SoftwareSystem) : ComponentDiagram {

    override val targetContainer: Container
        get() = targetSystem.getContainer(EContainerInstance.ORDER_SERVICE)

    override fun draw(workspace: Workspace): ComponentView {
        return workspace.views.createComponentView(targetContainer, key, "Component Diagram for Order Service").apply {
            addAllComponents()
            enableAutomaticLayout()
        }
    }
}
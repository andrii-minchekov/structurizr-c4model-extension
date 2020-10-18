package com.anmi.c4.view

import com.anmi.c4.diagram.SystemContextDiagram
import com.structurizr.Workspace
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.SystemContextView
import com.structurizr.view.addAllElementsRelatedWith

class DemoSystemContextDiagram(override val targetSystem: SoftwareSystem): SystemContextDiagram {
    override fun draw(workspace: Workspace): SystemContextView {
        return workspace.views.createSystemContextView(targetSystem, key, "System Context Diagram of E-Commerce System").apply {
            addAllElementsRelatedWith(targetSystem)
        }
    }
}
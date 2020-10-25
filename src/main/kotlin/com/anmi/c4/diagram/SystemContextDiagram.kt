package com.anmi.c4.diagram

import com.anmi.c4.model.element.ITag
import com.anmi.c4.util.refine
import com.structurizr.Workspace
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.SystemContextView
import com.structurizr.view.addAllElementsRelatedWith

interface SystemContextDiagram : Diagram<SystemContextView> {

    val targetSystem: SoftwareSystem

    override val key: String
        get() = "${targetSystem.name}_${type.name}".refine()

    override val type: DiagramType
        get() = DiagramType.CONTEXT

    override fun draw(workspace: Workspace, vararg tag: ITag): SystemContextView {
        return workspace.views.createSystemContextView(targetSystem, key, "System Context Diagram of ${targetSystem.name}").apply {
            enableAutomaticLayout()
            addAllElementsRelatedWith(targetSystem)
        }
    }
}

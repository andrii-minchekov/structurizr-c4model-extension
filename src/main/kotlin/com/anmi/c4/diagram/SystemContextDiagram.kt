package com.anmi.c4.diagram

import com.anmi.c4.model.element.ISystem
import com.anmi.c4.model.element.ITag
import com.anmi.c4.util.refine
import com.structurizr.Workspace
import com.structurizr.model.getSystem
import com.structurizr.view.SystemContextView
import com.structurizr.view.addAllElementsRelatedWith

interface SystemContextDiagram : Diagram<SystemContextView> {

    val targetSystem: ISystem

    override val key: String
        get() = "${targetSystem.label}_${type.name}".refine()

    override val type: DiagramType
        get() = DiagramType.CONTEXT

    override fun draw(workspace: Workspace, vararg tag: ITag): SystemContextView {
        val system = workspace.model.getSystem(targetSystem)
        return workspace.views.createSystemContextView(system, key, "System Context Diagram of ${targetSystem.label}").apply {
            enableAutomaticLayout()
            addAllElementsRelatedWith(system)
        }
    }
}

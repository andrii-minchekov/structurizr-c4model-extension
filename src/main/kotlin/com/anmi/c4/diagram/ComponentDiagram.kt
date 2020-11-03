package com.anmi.c4.diagram

import com.anmi.c4.model.element.IContainer
import com.anmi.c4.model.element.ISystem
import com.anmi.c4.model.element.ITag
import com.anmi.c4.util.refine
import com.structurizr.Workspace
import com.structurizr.model.getContainer
import com.structurizr.model.getSystem
import com.structurizr.view.AutomaticLayout
import com.structurizr.view.ComponentView
import com.structurizr.view.addElementsWithTag

interface ComponentDiagram : Diagram<ComponentView> {

    val targetSystem: ISystem
    val targetContainer: IContainer

    override val key: String
        get() = buildKey(targetSystem.label, targetContainer.label)

    override val type: DiagramType
        get() = DiagramType.COMPONENT

    override fun draw(workspace: Workspace, vararg tag: ITag): ComponentView {
        val container = workspace.model.getSystem(targetSystem).getContainer(targetContainer)
        return workspace.views.createComponentView(container, key, "Component Diagram for ${targetContainer.label}").apply {
            enableAutomaticLayout(AutomaticLayout.RankDirection.LeftRight, 300, 600, 200, false)
            addElementsWithTag(container.components, *tag)
        }
    }

    companion object {
        @JvmStatic
        fun buildKey(targetSystem: String, targetContainer: String): String {
            return "${targetSystem}_${targetContainer}_${DiagramType.COMPONENT.name}".refine()
        }
    }
}

package com.anmi.c4.diagram

import com.anmi.c4.model.element.ITag
import com.anmi.c4.util.refine
import com.structurizr.Workspace
import com.structurizr.model.Container
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.ComponentView
import com.structurizr.view.addElementsWithTag

interface ComponentDiagram : Diagram<ComponentView> {

    val targetSystem: SoftwareSystem
    val targetContainer: Container

    override val key: String
        get() = buildKey(targetSystem, targetContainer)

    override val type: DiagramType
        get() = DiagramType.COMPONENT

    override fun draw(workspace: Workspace, vararg tag: ITag): ComponentView {
        return workspace.views.createComponentView(targetContainer, key, "Component Diagram for ${targetContainer.name}").apply {
            enableAutomaticLayout()
            addElementsWithTag(targetContainer.components, *tag)
        }
    }

    companion object {
        @JvmStatic
        fun buildKey(targetSystem: SoftwareSystem, targetContainer: Container): String {
            return "${targetSystem.name}_${targetContainer.name}_${DiagramType.COMPONENT.name}".refine()
        }
    }
}

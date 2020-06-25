package com.anmi.c4.diagram

import com.anmi.c4.util.refine
import com.structurizr.model.Container
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.ComponentView

interface ComponentDiagram : Diagram<ComponentView> {

    val targetSystem: SoftwareSystem
    val targetContainer: Container

    override val key: String
        get() = buildKey(targetSystem, targetContainer)

    override val type: DiagramType
        get() = DiagramType.COMPONENT

    companion object {
        @JvmStatic
        fun buildKey(targetSystem: SoftwareSystem, targetContainer: Container): String {
            return "${targetSystem.name}_${targetContainer.name}_${DiagramType.COMPONENT.name}".refine()
        }
    }
}

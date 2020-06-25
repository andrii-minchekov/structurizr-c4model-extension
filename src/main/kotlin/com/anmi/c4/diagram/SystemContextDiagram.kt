package com.anmi.c4.diagram

import com.anmi.c4.util.refine
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.SystemContextView

interface SystemContextDiagram : Diagram<SystemContextView> {

    val targetSystem: SoftwareSystem

    override val key: String
        get() = "${targetSystem.name}_${type.name}".refine()

    override val type: DiagramType
        get() = DiagramType.CONTEXT
}

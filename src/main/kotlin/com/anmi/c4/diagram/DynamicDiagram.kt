package com.anmi.c4.diagram

import com.anmi.c4.util.refine
import com.structurizr.model.Element
import com.structurizr.view.DynamicView

interface DynamicDiagram<out E : Element> : Diagram<DynamicView> {

    override val type: DiagramType
        get() = DiagramType.DYNAMIC

    override val key: String
        get() = "${targetElement.name}_${type.name}".refine()

    val targetElement: E
}

package com.anmi.c4.diagram

import com.anmi.c4.model.element.IElement
import com.anmi.c4.util.refine
import com.structurizr.view.DynamicView

interface DynamicDiagram<out E : IElement> : Diagram<DynamicView> {

    override val type: DiagramType
        get() = DiagramType.DYNAMIC

    override val key: String
        get() = "${targetElement.label}_${type.name}".refine()

    val targetElement: E
}

package com.anmi.c4.diagram

import com.structurizr.view.SystemLandscapeView

interface LandscapeDiagram : Diagram<SystemLandscapeView> {

    override val type: DiagramType
        get() = DiagramType.LANDSCAPE
}

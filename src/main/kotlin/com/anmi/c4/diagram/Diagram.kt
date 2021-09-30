package com.anmi.c4.diagram

import com.anmi.c4.model.element.ITag
import com.structurizr.Workspace
import com.structurizr.view.View

interface Diagram<out V : View> {

    /**
     * identifier of the diagram
     * By this key you can reference then a diagram in a documentation template
     * Do not change this key because layout coordinates are directly linked to it
     */
    val key: String

    val type: DiagramType

    val autoLayoutEnabled: Boolean
        get() = true

    /**
     * Build relations between components regarded to the type of the diagram
     */
    fun draw(workspace: Workspace, vararg tag: ITag): V
}

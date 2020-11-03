package com.anmi.c4.diagram

import com.anmi.c4.model.element.ISystem
import com.anmi.c4.model.element.ITag
import com.anmi.c4.util.refine
import com.structurizr.Workspace
import com.structurizr.model.getSystem
import com.structurizr.view.AutomaticLayout
import com.structurizr.view.ContainerView
import com.structurizr.view.addAllElementsRelatedWith

interface ContainerDiagram : Diagram<ContainerView> {

    val targetSystem: ISystem

    override val key: String
        get() = "${targetSystem.label}_${type.name}".refine()

    override val type: DiagramType
        get() = DiagramType.CONTAINER

    override fun draw(workspace: Workspace, vararg tag: ITag): ContainerView {
        val system = workspace.model.getSystem(targetSystem)
        val containerView = workspace.views.createContainerView(system, key, "The container diagram for the ${targetSystem.label} System")
        containerView.enableAutomaticLayout(AutomaticLayout.RankDirection.TopBottom, 300, 600, 200, false)
        system.containers.forEach(containerView::addAllElementsRelatedWith)
        return containerView
    }
}

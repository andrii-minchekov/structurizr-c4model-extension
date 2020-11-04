package com.anmi.c4.demosystem

import com.anmi.c4.IWorkspace
import com.anmi.c4.config.Config
import com.anmi.c4.demosystem.model.DemoSystemModel
import com.anmi.c4.demosystem.model.EContainer.ORDER_SERVICE
import com.anmi.c4.demosystem.view.DemoSystemContainerDiagram
import com.anmi.c4.diagram.DefaultComponentDiagram
import com.anmi.c4.diagram.DefaultSystemContextDiagram
import com.anmi.c4.diagram.Diagram
import com.structurizr.view.DeploymentView
import com.structurizr.view.DynamicView

class DemoSystemWorkspace(override val cfg: Config) : IWorkspace {
    private val model = DemoSystemModel()

    override val spec = object : IWorkspace.ISpec {
        override val models = listOf(model)
        override val staticDiagrams = listOf(
                object : DefaultSystemContextDiagram(model.system) {},
                DemoSystemContainerDiagram(model.system),
                object : DefaultComponentDiagram(model.system, ORDER_SERVICE) {}
        )
        override val dynamicDiagrams: List<Diagram<DynamicView>> = emptyList()
        override val deploymentDiagrams: List<Diagram<DeploymentView>> = emptyList()
    }
}
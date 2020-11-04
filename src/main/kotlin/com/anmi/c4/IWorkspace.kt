package com.anmi.c4

import com.anmi.c4.config.Config
import com.anmi.c4.diagram.Diagram
import com.anmi.c4.model.element.SystemModel
import com.structurizr.Workspace
import com.structurizr.view.DeploymentView
import com.structurizr.view.DynamicView
import com.structurizr.view.StaticView
import com.structurizr.view.View


interface IWorkspace {
    val cfg: Config
    val spec: ISpec

    operator fun invoke() : Workspace{
        val workspace = createEmptyWorkspace(cfg)
        spec(workspace)
        return workspace
    }

    interface ISpec {
        val models: List<SystemModel>
        val staticDiagrams: List<Diagram<StaticView>>
        val dynamicDiagrams: List<Diagram<DynamicView>>
        val deploymentDiagrams: List<Diagram<DeploymentView>>

        operator fun invoke(workspace: Workspace) {
            runModels(workspace)
            runDiagrams(workspace)
        }

        private fun allDiagrams(): List<Diagram<View>> {
            val result = ArrayList<Diagram<View>>()
            result.addAll(staticDiagrams)
            result.addAll(dynamicDiagrams)
            result.addAll(deploymentDiagrams)
            return result
        }

        private fun runDiagrams(workspace: Workspace) {
            allDiagrams().forEach {
                it.draw(workspace)
            }
        }

        private fun runModels(workspace: Workspace) {
            models.forEach {
                it(workspace.model)
            }
        }
    }
}
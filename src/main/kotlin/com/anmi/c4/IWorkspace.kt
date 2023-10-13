package com.anmi.c4

import com.anmi.c4.config.Config
import com.anmi.c4.diagram.DefaultContainerDiagram
import com.anmi.c4.diagram.DefaultSystemContextDiagram
import com.anmi.c4.diagram.Diagram
import com.anmi.c4.documentation.ADocumentation
import com.anmi.c4.model.element.SystemModel
import com.structurizr.Workspace
import com.structurizr.model.SequentialIntegerIdGeneratorStrategy
import com.structurizr.model.stylize
import com.structurizr.model.upload
import com.structurizr.view.DeploymentView
import com.structurizr.view.DynamicView
import com.structurizr.view.StaticView
import com.structurizr.view.View


interface IWorkspace {
    val cfg: Config
    val spec: ISpec

    fun upload(): Workspace {
        return invoke().upload(cfg)
    }

    operator fun invoke(): Workspace {
        val workspace = createEmptyWorkspace(cfg)
        spec(workspace)
        ADocumentation(workspace)
        return workspace
    }

    interface ISpec {
        val models: List<SystemModel>
        val staticDiagrams: List<Diagram<StaticView>>
            get() = listOf(
                object : DefaultSystemContextDiagram(models.first().system) {},
                object : DefaultContainerDiagram(models.first().system) {}
            )
        val dynamicDiagrams: List<Diagram<DynamicView>>
            get() = emptyList()
        val deploymentDiagrams: List<Diagram<DeploymentView>>
            get() = emptyList()

        operator fun invoke(workspace: Workspace) {
            runModels(workspace)
            runDiagrams(workspace)
            workspace.stylize()
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

    companion object {
        fun createEmptyWorkspace(config: Config): Workspace {
            val workspace = Workspace("Enterprise Architecture - ${config.profileName}", "This is a Software Model of Enterprise.")
            workspace.model.setIdGenerator(SequentialIntegerIdGeneratorStrategy())
            return workspace
        }
    }
}
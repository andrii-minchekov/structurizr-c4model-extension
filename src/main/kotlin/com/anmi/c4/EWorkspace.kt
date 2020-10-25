package com.anmi.c4

import com.anmi.c4.EWorkspace.Companion.GENERATOR
import com.anmi.c4.config.Config
import com.anmi.c4.diagram.Diagram
import com.anmi.c4.diagram.style.Stylist
import com.anmi.c4.documentation.EDocumentation
import com.anmi.c4.model.element.SystemModel
import com.structurizr.Workspace
import com.structurizr.model.Model
import com.structurizr.model.SequentialIntegerIdGeneratorStrategy
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.getSystem
import com.structurizr.view.DeploymentView
import com.structurizr.view.DynamicView
import com.structurizr.view.StaticView
import com.structurizr.view.View
import java.lang.reflect.Constructor

class EWorkspace(config: Config, init: EWorkspaceSpec.() -> Unit) {
    val workspace = createEmptyWorkspace(config)

    init {
        EWorkspaceSpec().apply(init).apply {
            runModels()
            runDiagrams()
        }
        EDocumentation(workspace)
    }


    inner class EWorkspaceSpec {
        lateinit var models: List<SystemModel>
        lateinit var staticDiagrams: List<Diagram<StaticView>>
        lateinit var dynamicDiagrams: List<Diagram<DynamicView>>
        lateinit var deploymentDiagrams: List<Diagram<DeploymentView>>

        private fun allDiagrams(): List<Diagram<View>> {
            val result = ArrayList<Diagram<View>>()
            result.addAll(staticDiagrams)
            result.addAll(dynamicDiagrams)
            result.addAll(deploymentDiagrams)
            return result
        }

        internal fun runDiagrams() {
            allDiagrams().forEach {
                it.draw(workspace)
            }
        }

        internal fun runModels() {
            models.forEach {
                it(workspace.model)
            }
        }

        fun workspace(): Workspace {
            return this@EWorkspace.workspace
        }
    }

    companion object {
        val GENERATOR = SequentialIntegerIdGeneratorStrategy()
        fun simpleWorkspace(config: Config, targetModel: SystemModel, diagrams: (SoftwareSystem) -> List<Diagram<StaticView>>): Workspace {
            return EWorkspace(config) {
                this.models = listOf(targetModel)
                val targetSystem = workspace().model.getSystem(targetModel.system)
                this.staticDiagrams = diagrams(targetSystem)
                this.dynamicDiagrams = emptyList()
                this.deploymentDiagrams = emptyList()
            }.workspace
        }
    }
}

fun createEmptyWorkspace(config: Config): Workspace {
    val workspace = Workspace("Enterprise Architecture - ${config.profileName}", "This is a Software Model of Enterprise.")
    workspace.model.setIdGenerator(GENERATOR)
    Stylist(workspace).style()
    return workspace
}


fun createModel(): Model {
    return try {
        val constructor: Constructor<*> = Model::class.java.getDeclaredConstructor()
        constructor.isAccessible = true
        constructor.newInstance() as Model
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}


package com.anmi.c4.demosystem

import com.anmi.c4.EWorkspace
import com.anmi.c4.WorkspaceUploader
import com.anmi.c4.config.ConfigInstance
import com.anmi.c4.config.StructurizrFactory
import com.anmi.c4.demosystem.model.DemoSystemModel
import com.anmi.c4.demosystem.model.EContainer
import com.anmi.c4.demosystem.view.DemoSystemContainerDiagram
import com.anmi.c4.diagram.DefaultComponentDiagram
import com.anmi.c4.diagram.DefaultSystemContextDiagram
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class GenerateDiagramTest {

    @Disabled
    @Test
    fun `should create remote workspace with demo model`() {
        val config = ConfigInstance.TEST
        val model = DemoSystemModel()
        val workspace = EWorkspace.simpleWorkspace(config, model) {
            listOf(
                    object : DefaultSystemContextDiagram(model.system) {},
                    DemoSystemContainerDiagram(model.system),
                    object : DefaultComponentDiagram(model.system, EContainer.ORDER_SERVICE) {}
            )
        }
        WorkspaceUploader.upload(workspace, config)

        StructurizrFactory.client(config).getWorkspace(config.workspaceId).apply {
            assertThat(workspace.model.softwareSystems.firstOrNull { it.name == model.system.label }).isNotNull()
        }
    }
}
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
import com.structurizr.model.getContainer
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

class GenerateDiagramTest {

    @Test
    fun `should create remote workspace with demo model`() {
        val config = ConfigInstance.TEST
        val targetModel = DemoSystemModel()
        val workspace = EWorkspace.simpleWorkspace(config, targetModel) {
            listOf(
                    object : DefaultSystemContextDiagram(it) {},
                    DemoSystemContainerDiagram(it),
                    object : DefaultComponentDiagram(it, it.getContainer(EContainer.ORDER_SERVICE)) {}
            )
        }
        WorkspaceUploader.upload(workspace, config)

        StructurizrFactory.client(config).getWorkspace(config.workspaceId).apply {
            assertThat(model.softwareSystems.firstOrNull { it.name == targetModel.system.label }).isNotNull()
        }
    }
}
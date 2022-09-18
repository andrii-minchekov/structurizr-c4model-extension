package com.anmi.c4.demosystem

import com.anmi.c4.config.*
import com.anmi.c4.demosystem.model.DemoSystem
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

class GenerateDiagramTest {

    @Test
    fun `should create remote workspace with demo model`() {
        val config = ConfigInstance.TEST
        val workspace = DemoSystemWorkspace(config).upload()

        StructurizrFactory.client(config).getWorkspace(config.workspaceId).apply {
            assertThat(workspace.model.softwareSystems.first { it.name == DemoSystem.DEMO_SYSTEM.label }).isNotNull()
        }
    }
}
package com.anmi.c4.demosystem

import com.anmi.c4.config.ConfigInstance
import com.anmi.c4.config.StructurizrFactory
import com.anmi.c4.demosystem.model.ESystem
import com.structurizr.model.upload
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class GenerateDiagramTest {

    @Disabled
    @Test
    fun `should create remote workspace with demo model`() {
        val config = ConfigInstance.TEST
        val workspace = DemoSystemWorkspace(config)().upload(config)

        StructurizrFactory.client(config).getWorkspace(config.workspaceId).apply {
            assertThat(workspace.model.softwareSystems.firstOrNull { it.name == ESystem.DEMO_SYSTEM.label }).isNotNull()
        }
    }
}
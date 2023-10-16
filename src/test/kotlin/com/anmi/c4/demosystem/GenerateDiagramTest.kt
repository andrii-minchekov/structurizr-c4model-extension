package com.anmi.c4.demosystem

import com.anmi.c4.config.ConfigInstance
import com.anmi.c4.config.StructurizrFactory
import com.anmi.c4.demosystem.model.DemoSystem
import com.structurizr.model.Location
import com.structurizr.model.upload
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

class GenerateDiagramTest {

    @Test
    fun `should create remote workspace with demo model`() {
        val config = ConfigInstance.TEST
        val workspace = DemoSystemWorkspace(config)()


        assertThat(workspace.model.softwareSystems.first { it.location == Location.External && it.name == DemoSystem.THIRD_PARTY_SYSTEM.label}.tags)
            .isEqualTo("Element,Software System,External Software System,Third Party System")

        workspace.upload(config)
        StructurizrFactory.client(config).getWorkspace(config.workspaceId).apply {
            assertThat(workspace.model.softwareSystems.first { it.name == DemoSystem.DEMO_SYSTEM.label }).isNotNull()
        }
    }
}
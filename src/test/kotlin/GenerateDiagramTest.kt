import com.anmi.c4.EWorkspace
import com.anmi.c4.WorkspaceUploader
import com.anmi.c4.config.ConfigInstance
import com.anmi.c4.config.StructurizrFactory
import com.anmi.c4.model.DemoSystemModel
import com.anmi.c4.model.ESoftwareSystemInstance
import com.anmi.c4.view.DemoSystemContainerDiagram
import com.anmi.c4.view.DemoSystemContextDiagram
import com.anmi.c4.view.OrderServiceComponentDiagram
import com.structurizr.Workspace
import com.structurizr.model.getSystem
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

class GenerateDiagramTest {

    @Test
    fun `should create remote workspace with models`() {
        val config = ConfigInstance.TEST
        val workspace = createTestWorkspace(config)

        WorkspaceUploader.upload(workspace, config)

        val client = StructurizrFactory.client(config)
        val remoteWorkspace = client.getWorkspace(config.workspaceId)

        val componentsFromDifferentSourceTree = remoteWorkspace.model.softwareSystems
        assertThat(componentsFromDifferentSourceTree).hasSizeGreaterThan(0)
    }

    private fun createTestWorkspace(config: ConfigInstance): Workspace {
        return EWorkspace(config) {
            val workspace = this.workspace()
            this.models = listOf(DemoSystemModel(workspace.model))
            val targetSystem = workspace.model.getSystem(ESoftwareSystemInstance.DEMO_SYSTEM)
            this.diagrams = listOf(
                    DemoSystemContextDiagram(targetSystem),
                    DemoSystemContainerDiagram(targetSystem),
                    OrderServiceComponentDiagram(targetSystem)
            )
        }.workspace
    }
}
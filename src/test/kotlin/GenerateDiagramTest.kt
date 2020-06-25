import com.anmi.c4.Runner
import com.anmi.c4.config.Config
import com.anmi.c4.config.StructurizrFactory
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

class GenerateDiagramTest {

    @Test
    fun `should merge source tree workspace with modified cloud and push to server`() {
        val profile = "TEST"
        Runner.main(arrayOf(profile))

        val config = Config.valueOf(profile)

        val client = StructurizrFactory.client(config)
        val workspace = client.getWorkspace(config.workspaceId)

        val componentsFromDifferentSourceTree = workspace.model.softwareSystems
        assertThat(componentsFromDifferentSourceTree).hasSizeGreaterThan(0)
    }
}
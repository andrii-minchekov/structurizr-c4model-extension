package com.anmi.c4.util

import com.anmi.c4.config.ConfigInstance
import org.junit.jupiter.api.Test

class CopyRemoteWorkspaceToLocalRunnerTest {

    @Test
    fun `should copy remote workspace to local dir`() {
        val destinationSubFolder = "build/resources/test/documentation"
        val workspace = CopyRemoteWorkspaceToLocalRunner.run(ConfigInstance.TEST, destinationSubFolder)
        ExportTechStackFromModelToFile.export(workspace, destinationSubFolder)
    }
}
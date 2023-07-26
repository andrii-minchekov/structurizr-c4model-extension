package com.anmi.c4.util

import com.anmi.c4.config.ConfigInstance
import org.junit.jupiter.api.Test

class CopyRemoteWorkspaceToLocalRunnerTest {

    @Test
    fun `should copy remote workspace to local dir`() {
        CopyRemoteWorkspaceToLocalRunner.run(ConfigInstance.TEST, "build/resources/test/documentation")
    }
}
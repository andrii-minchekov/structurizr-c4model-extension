@file:JvmName("Runner")

package com.anmi.c4.model

import com.anmi.c4.EWorkspace
import com.anmi.c4.WorkspaceUploader
import com.anmi.c4.config.ConfigInstance

class Runner {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            require(args.size == 1 && ConfigInstance.values().map { it.name }.contains(args[0])) {
                "Specify configuration profile to be used either PROD or TEST"
            }
            val config = ConfigInstance.valueOf(args[0])
            WorkspaceUploader.mergeAndUpload(EWorkspace(config){}.workspace, config)
        }
    }
}


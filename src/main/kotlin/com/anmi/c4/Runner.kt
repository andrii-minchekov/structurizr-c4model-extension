@file:JvmName("Runner")

package com.anmi.c4

import com.anmi.c4.config.Config
import com.anmi.c4.config.StructurizrFactory
import com.structurizr.model.mergeInto
import org.slf4j.LoggerFactory

class Runner {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            require(args.size == 1 && Config.values().map { it.name }.contains(args[0])) {
                "Specify configuration profile to be used either PROD or TEST"
            }
            val config = Config.valueOf(args[0])

            val workspace = EWorkspace(config).workspace
            val structurizrClient = StructurizrFactory.client(config)
            val workspaceId = config.workspaceId
            val remoteWorkspace = structurizrClient.getWorkspace(workspaceId)
            workspace.mergeInto(remoteWorkspace)
            if (structurizrClient.unlockWorkspace(workspaceId)) {
                LoggerFactory.getLogger(Runner::class.java).info("Workspace $workspaceId was unlocked")
            }
            structurizrClient.putWorkspace(workspaceId, workspace)
            LoggerFactory.getLogger(Runner::class.java).info("C4 Cloud workspace $workspaceId was updated successfully")
        }
    }
}

package com.anmi.c4

import com.anmi.c4.config.Config
import com.anmi.c4.config.StructurizrFactory
import com.structurizr.Workspace
import com.structurizr.model.mergeInto
import org.slf4j.LoggerFactory

object WorkspaceUploader {
    fun mergeAndUpload(workspace: Workspace, config: Config) {
        val structurizrClient = StructurizrFactory.client(config)
        val workspaceId = config.workspaceId
        val remoteWorkspace = structurizrClient.getWorkspace(workspaceId)
        workspace.mergeInto(remoteWorkspace)
        if (structurizrClient.unlockWorkspace(workspaceId)) {
            LoggerFactory.getLogger(WorkspaceUploader::class.java).info("Workspace $workspaceId was unlocked")
        }
        structurizrClient.putWorkspace(workspaceId, workspace)
        LoggerFactory.getLogger(WorkspaceUploader::class.java).info("C4 Cloud workspace $workspaceId was updated successfully")
    }

    fun upload(workspace: Workspace, config: Config) {
        val structurizrClient = StructurizrFactory.client(config)
        val workspaceId = config.workspaceId
        if (structurizrClient.unlockWorkspace(workspaceId)) {
            LoggerFactory.getLogger(WorkspaceUploader::class.java).info("Workspace $workspaceId was unlocked")
        }
        structurizrClient.putWorkspace(workspaceId, workspace)
        LoggerFactory.getLogger(WorkspaceUploader::class.java).info("C4 Cloud workspace $workspaceId was updated successfully")
    }
}
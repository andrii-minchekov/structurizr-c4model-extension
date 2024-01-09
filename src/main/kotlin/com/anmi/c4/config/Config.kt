package com.anmi.c4.config

import com.structurizr.configuration.WorkspaceScope

interface Config {
    val archiveLocation: String
    val apiKey: String
    val apiSecret: String
    val workspaceId: Long
    val profileName: String
    val scope: WorkspaceScope
}

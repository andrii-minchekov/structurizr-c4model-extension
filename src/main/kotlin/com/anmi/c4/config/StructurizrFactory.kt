package com.anmi.c4.config

import com.structurizr.api.StructurizrClient
import java.io.File

object StructurizrFactory {
    fun client(config: Config): StructurizrClient {
        val structurizrClient = StructurizrClient(config.apiKey, config.apiSecret)
        structurizrClient.workspaceArchiveLocation = File(config.archiveLocation)
        return structurizrClient
    }
}
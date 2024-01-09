package com.anmi.c4.config

import com.structurizr.configuration.WorkspaceScope
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object ConfigCreator {

    fun fromFile(cfgFileName: String): Config {
        val props: Properties = properties(cfgFileName)
        val archiveLocation = requireNotNull(props.getProperty(Const.ARCH_LOCATION_KEY, ""))
        val apiKey = requireNotNull(props.getProperty(Const.API_KEY))
        val apiSecret = requireNotNull(props.getProperty(Const.API_SECRET))
        val workspaceId = requireNotNull(props.getProperty(Const.WORKSPACE_ID_KEY)).toLong()
        val profileName = requireNotNull(props.getProperty(Const.PROFILE_NAME))
        return object : Config {
            override val archiveLocation = archiveLocation
            override val apiKey = apiKey
            override val apiSecret = apiSecret
            override val workspaceId = workspaceId
            override val profileName = profileName
            override val scope: WorkspaceScope = WorkspaceScope.SoftwareSystem
        }
    }

    private fun properties(cfgFileName: String): Properties {
        val props = Properties()
        System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2")
        Thread.currentThread().contextClassLoader.getResourceAsStream(cfgFileName).use {
            props.load(it)
        }
        val pathname = props.getProperty(Const.ARCH_LOCATION_KEY)
        Files.createDirectories(Paths.get(pathname))
        return props
    }

    fun fromId(workspaceId: Long): ConfigInstance {
        return ConfigInstance.values().firstOrNull { config -> config.workspaceId == workspaceId }
                ?: throw IllegalArgumentException("Workspace $workspaceId is not supported")
    }

    private object Const {
        internal const val ARCH_LOCATION_KEY = "archiveLocation"
        internal const val WORKSPACE_ID_KEY = "workspaceId"
        internal const val API_KEY = "apiKey"
        internal const val API_SECRET = "apiSecret"
        internal const val PROFILE_NAME = "profileName"
    }
}
package com.anmi.c4.config

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

enum class Config {
    PROD {
        override fun cfgFileName() = "config.properties"
    },
    TEST {
        override fun cfgFileName() = "test-config.properties"
    };

    abstract fun cfgFileName(): String
    private val props = properties()
    val archiveLocation = props.getProperty(Const.ARCH_LOCATION_KEY)!!
    val apiKey = props.getProperty(Const.API_KEY)!!
    val workspaceId = props.getProperty(Const.WORKSPACE_ID_KEY).toLong()
    val apiSecret = props.getProperty(Const.API_SECRET)!!
    val profileName = props.getProperty(Const.PROFILE_NAME)!!

    private fun properties(): Properties {
        val props = Properties()
        System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2")
        Thread.currentThread().contextClassLoader.getResourceAsStream(cfgFileName()).use {
            props.load(it)
        }
        val pathname = props.getProperty(Const.ARCH_LOCATION_KEY)
        Files.createDirectories(Paths.get(pathname))
        return props
    }

    companion object {
        @JvmStatic
        fun from(workspaceId: Long): Config {
            return values().firstOrNull { config -> config.workspaceId == workspaceId }
                    ?: throw IllegalArgumentException("Workspace $workspaceId is not supported")
        }
    }

    private object Const {
        internal const val ARCH_LOCATION_KEY = "archiveLocation"
        internal const val WORKSPACE_ID_KEY = "workspaceId"
        internal const val API_KEY = "apiKey"
        internal const val API_SECRET = "apiSecret"
        internal const val PROFILE_NAME = "profileName"
    }
}


package com.anmi.c4.config

interface Config {
    val archiveLocation: String
    val apiKey: String
    val apiSecret: String
    val workspaceId: Long
    val profileName: String
}

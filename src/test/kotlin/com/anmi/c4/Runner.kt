@file:JvmName("Runner")

package com.anmi.c4

import com.anmi.c4.config.ConfigInstance
import com.anmi.c4.demosystem.DemoSystemWorkspace
import com.structurizr.model.upload

class Runner {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val config = ConfigInstance.TEST
            DemoSystemWorkspace(config)().upload(config)
        }
    }
}


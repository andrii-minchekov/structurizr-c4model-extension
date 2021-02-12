@file:JvmName("Runner")

package com.anmi.c4

import com.anmi.c4.config.ConfigInstance
import com.anmi.c4.demosystem.DemoSystemWorkspace

class Runner {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            DemoSystemWorkspace(ConfigInstance.TEST).upload()
        }
    }
}


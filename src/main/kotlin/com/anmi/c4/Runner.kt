@file:JvmName("Runner")

package com.anmi.c4

import com.structurizr.model.upload

class Runner {
    companion object {
        @JvmStatic
        fun run(workspace: IWorkspace) {
            workspace().upload(workspace.cfg)
        }
    }
}


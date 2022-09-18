package com.anmi.c4.demosystem

import com.anmi.c4.IWorkspace
import com.anmi.c4.config.Config
import com.anmi.c4.demosystem.model.DemoSystemModel

class DemoSystemWorkspace(override val cfg: Config) : IWorkspace {
    override val spec = object : IWorkspace.ISpec {
        override val models = listOf(DemoSystemModel())
    }
}
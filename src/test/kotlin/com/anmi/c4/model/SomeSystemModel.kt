package com.anmi.c4.model

import com.anmi.c4.model.EContainerInstance.SOME_SERVER
import com.anmi.c4.model.element.SystemModel
import com.structurizr.model.Model
import com.structurizr.model.addContainer
import com.structurizr.model.addSystem

class SomeSystemModel(val model: Model) : SystemModel {
    override val system = model.addSystem(ESoftwareSystemInstance.SOME_SYSTEM)
    val container = system.addContainer(SOME_SERVER)
}
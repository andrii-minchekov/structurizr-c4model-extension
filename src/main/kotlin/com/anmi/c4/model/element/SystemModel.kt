package com.anmi.c4.model.element

import com.structurizr.model.Model
import com.structurizr.model.SoftwareSystem
import com.structurizr.model.getSystem

interface SystemModel {
    val system: ISystem
    val context: Array<ISystemRelationship>
    val containers: Array<IContainerRelationship>
    val components: Array<IComponentRelationship>

    operator fun invoke(model: Model) {
        runContext(model)
        runContainer(model.getSystem(system))
        runComponents(model.getSystem(system))
    }

    private fun runContext(model: Model) {
        context.forEach {
            it(model)
        }
    }

    private fun runContainer(system: SoftwareSystem) {
        containers.forEach {
            it(system)
        }
    }

    private fun runComponents(system: SoftwareSystem) {
        components.forEach {
            it(system)
        }
    }
}

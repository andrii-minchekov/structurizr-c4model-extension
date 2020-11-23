package com.anmi.c4.demosystem.model

import com.anmi.c4.analysis.ComponentFinderParams
import com.anmi.c4.analysis.LocalPathToGitUrl
import com.anmi.c4.analysis.Packages
import com.anmi.c4.analysis.Sources
import com.anmi.c4.demosystem.model.ETechnology.REST
import com.anmi.c4.model.element.IComponentRelationship
import com.anmi.c4.model.element.ISystem
import com.anmi.c4.model.element.SystemModel
import java.io.File

class DemoSystemModel(override val system: ISystem = ESystem.DEMO_SYSTEM) : SystemModel {
    override val context = arrayOf(
            EPerson.WEB_USER.uses(ESystem.DEMO_SYSTEM, "Uses")
    )
    override val containers = arrayOf(
            EContainer.ORDER_SERVICE.uses(EContainer.USER_SERVICE, "Obtain user information", REST)
    )
    override val components = arrayOf(
            EComponent.ORDER_CONTROLLER.uses(EComponent.ORDER_SERVICE, "Delegate calls to domain"),
            IComponentRelationship.AutoScannedComponents(EComponent.ORDER_CONTROLLER.container,
                    ComponentFinderParams(
                            Packages(setOf("com.anmi.c4.demosystem")),
                            Sources(setOf("src/test/java"),
                                    LocalPathToGitUrl(
                                            File("."),
                                            "https://github.com/aminchekov/structurizr-c4model-extension",
                                            "master"
                                    )
                            )
                    )
            )
    )
}
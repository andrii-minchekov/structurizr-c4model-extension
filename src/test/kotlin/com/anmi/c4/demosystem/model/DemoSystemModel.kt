package com.anmi.c4.demosystem.model

import com.anmi.c4.demosystem.model.ETechnology.REST
import com.anmi.c4.model.element.*

class DemoSystemModel(override val system: ISystem = ESystem.DEMO_SYSTEM) : SystemModel {
    override val context = arrayOf(
            EPerson.WEB_USER.uses(ESystem.DEMO_SYSTEM, "Uses"),
            ESystem.DEMO_SYSTEM.uses(ESystem.THIRD_PARTY_SYSTEM, "Uses")
    )
    override val containers = arrayOf(
            EContainer.ORDER_SERVICE.uses(EContainer.USER_SERVICE, "Obtain user information", REST)
    )
    //run with Java 8 to have auto scanned components in the model
//    override val components = arrayOf(
//            EComponent.ORDER_CONTROLLER.uses(EComponent.ORDER_SERVICE, "Delegate calls to domain"),
//            IComponentRelationship.AutoScannedComponents(EComponent.ORDER_CONTROLLER.container,
//                    ComponentFinderParams(
//                            Packages(setOf("com.anmi.c4.demosystem")),
//                            Sources(setOf("src/test/java"),
//                                    LocalPathToGitUrl(
//                                            File("."),
//                                            "https://github.com/aminchekov/structurizr-c4model-extension",
//                                            "master"
//                                    )
//                            )
//                    )
//            )
//    )
}
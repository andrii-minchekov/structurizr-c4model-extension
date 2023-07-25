package com.anmi.c4.demosystem.model

import com.anmi.c4.demosystem.model.DemoTechnology.REST
import com.anmi.c4.model.element.ISystem
import com.anmi.c4.model.element.ISystemRelationship
import com.anmi.c4.model.element.SystemModel

class DemoSystemModel(override val system: ISystem = DemoSystem.DEMO_SYSTEM) : SystemModel {
    override val context = emptyArray<ISystemRelationship>()
    override val containers = arrayOf(
        DemoPerson.WEB_USER.uses(DemoContainer.ORDER_SERVICE, "Uses"),
        DemoContainer.ORDER_SERVICE.uses(DemoContainer.INVENTORY_SERVICE, "Obtain user information", REST),
        DemoContainer.INVENTORY_SERVICE.uses(DemoSystem.THIRD_PARTY_SYSTEM, "Authenticate", REST)
    )
    override val components = arrayOf(
        DemoComponent.ORDER_CONTROLLER.uses(DemoComponent.ORDER_SERVICE, "Delegate calls to domain")
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
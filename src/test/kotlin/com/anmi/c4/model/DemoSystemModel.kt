package com.anmi.c4.model

import com.anmi.c4.analysis.ComponentFinderParams
import com.anmi.c4.analysis.LocalPathToGitUrl
import com.anmi.c4.analysis.Packages
import com.anmi.c4.analysis.Sources
import com.anmi.c4.model.element.SystemModel
import com.structurizr.model.Model
import com.structurizr.model.addComponentsFrom
import com.structurizr.model.addContainer
import com.structurizr.model.addNewComponent
import com.structurizr.model.addSystem
import com.structurizr.model.usedBy
import java.io.File

class DemoSystemModel(val model: Model) : SystemModel {
    override val system = with(model) {
        val system = addSystem(ESoftwareSystemInstance.DEMO_SYSTEM).apply {
            usedBy(EPersonInstance.WEB_USER, "Uses")
        }
        val userService = system.addContainer(EContainerInstance.USER_SERVICE)

        val orderService = system.addContainer(EContainerInstance.ORDER_SERVICE) {
            uses(userService, "Obtain user information", TechnologyInstance.REST.label)
        }
        orderService.apply {
            val orderController = addNewComponent("OrderController", "Accept client requests", TechnologyInstance.JAVA_11) {}
            addNewComponent("OrderService", "Manage Orders", TechnologyInstance.JAVA_11) {
                uses(orderController, "Uses")
            }
            addComponentsFrom(ComponentFinderParams(
                    Packages(setOf("com.anmi.c4.model")),
                    Sources(setOf("src/test/java"),
                            LocalPathToGitUrl(
                                    File("."),
                                    "https://github.com/aminchekov/structurizr-c4model-extension",
                                    "master"
                            )
                    )
            ))
        }
        system
    }
}
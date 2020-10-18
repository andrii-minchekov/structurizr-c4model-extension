package com.anmi.c4.model

import com.anmi.c4.model.element.EContainer

enum class EContainerInstance(override val label: String, override val description: String, override val technologies: Array<String>) : EContainer {
    USER_SERVICE("User Service", "User Management Service", arrayOf(TechnologyInstance.JAX_RS.toString(), TechnologyInstance.SPRING_BOOT_2_1.toString(), TechnologyInstance.JAVA_11.toString())),
    ORDER_SERVICE("Order Service", "Order Management Service", arrayOf(TechnologyInstance.JAX_RS.toString(), TechnologyInstance.SPRING_BOOT_2_1.toString(), TechnologyInstance.JAVA_11.toString()));
}
package com.anmi.c4.demosystem.model

import com.anmi.c4.demosystem.model.ESystem.DEMO_SYSTEM
import com.anmi.c4.demosystem.model.ETechnology.JAVA_11
import com.anmi.c4.demosystem.model.ETechnology.JAX_RS
import com.anmi.c4.demosystem.model.ETechnology.SPRING_BOOT_2_1
import com.anmi.c4.model.element.IContainer
import com.anmi.c4.model.element.ISystem
import com.anmi.c4.model.element.Technology

enum class EContainer(override val system: ISystem, override val label: String, override val description: String, override val technologies: Array<Technology>) : IContainer {
    USER_SERVICE(DEMO_SYSTEM, "User Service", "User Management Service", arrayOf(JAX_RS, SPRING_BOOT_2_1, JAVA_11)),
    ORDER_SERVICE(DEMO_SYSTEM, "Order Service", "Order Management Service", arrayOf(JAX_RS, SPRING_BOOT_2_1, JAVA_11));
}
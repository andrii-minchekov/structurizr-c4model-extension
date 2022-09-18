package com.anmi.c4.demosystem.model

import com.anmi.c4.demosystem.model.DemoSystem.DEMO_SYSTEM
import com.anmi.c4.demosystem.model.DemoTechnology.*
import com.anmi.c4.model.element.*

enum class DemoContainer(override val system: ISystem, override val label: String, override val description: String, override val technologies: Array<Technology>, override val tags: Array<ITag> = emptyArray()) : IContainer {
    INVENTORY_SERVICE(DEMO_SYSTEM, "Inventory Service", "Track and manage inventory ", arrayOf(JAX_RS, SPRING_BOOT_2_1, JAVA_11)),
    ORDER_SERVICE(DEMO_SYSTEM, "Order Service", "Order Management Service", arrayOf(JAX_RS, SPRING_BOOT_2_1, JAVA_11));
}
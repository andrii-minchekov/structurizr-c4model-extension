package com.anmi.c4.demosystem

import com.anmi.c4.IWorkspace
import com.anmi.c4.config.Config
import com.anmi.c4.demosystem.DemoContainer.*
import com.anmi.c4.demosystem.DemoPerson.*
import com.anmi.c4.demosystem.DemoSystem.*
import com.anmi.c4.demosystem.model.DemoTechnology.*
import com.anmi.c4.model.element.*
import com.structurizr.model.Location

class DemoSystemWorkspaceSingleClass(override val cfg: Config) : IWorkspace {
    override val spec = object : IWorkspace.ISpec {
        override val models = listOf(DemoSystemModel())
    }
}

class DemoSystemModel(override val system: ISystem = DEMO_SYSTEM) : SystemModel {
    override val context = emptyArray<ISystemRelationship>()
    override val containers = arrayOf(
        WEB_USER.uses(DEMO_SYSTEM, ORDER_SERVICE, "Uses"),
        ORDER_SERVICE.uses(INVENTORY_SERVICE, "Obtain inventory", REST),
        INVENTORY_SERVICE.uses(THIRD_PARTY_SYSTEM, "Check suppliers", REST)
    )
}
enum class DemoSystem(
    override val location: Location, override val label: String, override val description: String, override val tags: Array<ITag> = emptyArray()
) : ISystem {
    DEMO_SYSTEM(Location.Internal, "Demo System", "Demo System description", arrayOf(ITag.OTHER_SYSTEMS_TAG)),
    THIRD_PARTY_SYSTEM(Location.External, "Third Party System", "Third Party System API"),
}

enum class DemoPerson(override val label: String, override val description: String, override val tags: Array<ITag> = emptyArray()) : IPerson {
    WEB_USER("User", "A user")
}

enum class DemoContainer(
    override val system: ISystem, override val label: String, override val description: String,
    override val technologies: Array<Technology>, override val tags: Array<ITag> = emptyArray()
) : IContainer {
    INVENTORY_SERVICE(DEMO_SYSTEM, "Inventory Service", "Track and manage inventory ", arrayOf(JAX_RS, SPRING_BOOT_2_1, JAVA_11)),
    ORDER_SERVICE(DEMO_SYSTEM, "Order Service", "Order Management Service", arrayOf(JAX_RS, SPRING_BOOT_2_1, JAVA_11));
}
package com.anmi.c4.model

import com.anmi.c4.model.element.EContainer

enum class EContainerInstance(override val label: String, override val description: String, override val technologies: Array<String>) : EContainer {
    SOME_SERVER("Some Server", "Some Server", arrayOf(TechnologyInstance.JAX_RS.toString(), TechnologyInstance.SPRING_BOOT_2_1.toString(), TechnologyInstance.JAVA_11.toString()));
}
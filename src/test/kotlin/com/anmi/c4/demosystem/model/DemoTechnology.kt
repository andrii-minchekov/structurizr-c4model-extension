package com.anmi.c4.demosystem.model

import com.anmi.c4.model.element.Technology

enum class DemoTechnology(override val label: String, override var version: String = "") : Technology {
    JAVA_11("Java", "11"),
    SPRING_BOOT_2_1("Spring Boot", "2.1"),
    JAX_RS("JAX-RS"),
    REST("HTTP/S REST");

    override fun toString(): String {
        return "'$label '$version"
    }
}
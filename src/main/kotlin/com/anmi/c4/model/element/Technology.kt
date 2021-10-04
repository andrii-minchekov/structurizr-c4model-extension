package com.anmi.c4.model.element

interface Technology {
    val label: String
    var version: String

    companion object {
        fun stringify(technology: Array<out Technology>): String? {
            return technology.takeIf { it.isNotEmpty() }?.mapNotNull { it.label + " " + it.version }?.joinToString()
        }
    }
}

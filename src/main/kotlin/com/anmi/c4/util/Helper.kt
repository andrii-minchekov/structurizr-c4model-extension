package com.anmi.c4.util

fun String.refine() : String{
    return this.replace(" ", "-").toLowerCase()
}
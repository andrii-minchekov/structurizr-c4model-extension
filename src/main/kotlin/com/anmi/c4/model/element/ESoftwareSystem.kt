package com.anmi.c4.model.element

import com.structurizr.model.Location

interface ESoftwareSystem {
    val location: Location
    val label: String
    val description: String
    val tag: Array<ETag>
}
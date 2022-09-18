package com.anmi.c4.demosystem.model

import com.anmi.c4.model.element.*
import com.anmi.c4.model.element.ITag.OTHER_SYSTEMS_TAG
import com.structurizr.model.Location

enum class DemoSystem(override val location: Location, override val label: String, override val description: String, override val tags: Array<ITag> = emptyArray()) : ISystem {
    DEMO_SYSTEM(Location.Internal, "Demo System", "Demo System description", arrayOf(OTHER_SYSTEMS_TAG)),
    THIRD_PARTY_SYSTEM(Location.External, "Third Party System", "Third Party System API"),
    PLAYGROUND(Location.Internal, "PLAYGROUND_SYSTEM", "PLAYGROUND SYSTEM description", arrayOf(OTHER_SYSTEMS_TAG)),
}

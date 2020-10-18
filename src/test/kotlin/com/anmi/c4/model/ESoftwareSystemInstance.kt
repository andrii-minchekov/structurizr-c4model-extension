package com.anmi.c4.model

import com.anmi.c4.model.element.ESoftwareSystem
import com.anmi.c4.model.element.ETag
import com.anmi.c4.model.element.ETag.OTHER_SYSTEMS_TAG
import com.structurizr.model.Location

enum class ESoftwareSystemInstance(override val location: Location, override val label: String, override val description: String, override val tag: Array<ETag>) : ESoftwareSystem {
    DEMO_SYSTEM(Location.Internal, "Demo System", "Demo System description", arrayOf(OTHER_SYSTEMS_TAG)),
    PLAYGROUND(Location.Internal, "PLAYGROUND_SYSTEM", "PLAYGROUND SYSTEM description", arrayOf(OTHER_SYSTEMS_TAG)),
}

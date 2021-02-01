package com.anmi.c4.demosystem.model

import com.anmi.c4.model.element.*

enum class EPerson(override val label: String, override val description: String, override val tags: Array<ITag> = emptyArray()) : IPerson {
    WEB_USER("User", "A user"),
    MOBILE_USER("Mobile User", "Mobile user"),
    API_CONSUMER("API Consumer", "Company which consumes data from API"),
}

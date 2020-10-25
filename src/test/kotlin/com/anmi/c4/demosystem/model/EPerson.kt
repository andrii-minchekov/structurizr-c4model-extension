package com.anmi.c4.demosystem.model

import com.anmi.c4.model.element.IPerson

enum class EPerson(override val label: String, override val description: String) : IPerson {
    WEB_USER("User", "A user"),
    MOBILE_USER("Mobile User", "Mobile user"),
    API_CONSUMER("API Consumer", "Company which consumes data from API"),
}

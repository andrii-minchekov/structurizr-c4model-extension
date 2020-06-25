package com.anmi.c4.model

import com.anmi.c4.model.element.EPerson

enum class EPersonInstance(override val label: String, override val description: String) : EPerson {
    WEB_USER("User", "A user"),
    MOBILE_USER("Mobile User", "Mobile user"),
    API_CONSUMER("API Consumer", "Company which consumes data from API"),
}

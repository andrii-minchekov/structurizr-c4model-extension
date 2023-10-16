package com.anmi.c4.model.element

enum class ATag(override val label: String) : ITag {
    WEB_BROWSER("Web Browser"),
    MOBILE_PHONE("Mobile Phone"),
    DATABASE("Database"),
    MESSAGE_BROKER("Message Broker"),
    EXTERNAL_SYSTEM("External Software System"),

    ERD_COMPONENT("ERD Component"),
    STATE_COMPONENT("State Component"),
    SPRING_REST_CONTROLLER("Spring Rest Controller"),
    SPRING_SERVICE("Spring Service"),
    SPRING_REPOSITORY("Spring Repository")
}

interface ITag {
    val label: String
}
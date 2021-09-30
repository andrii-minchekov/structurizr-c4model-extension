package com.anmi.c4.diagram.style

import com.anmi.c4.model.element.ITag.*
import com.structurizr.Workspace
import com.structurizr.model.Location
import com.structurizr.model.Tags.*
import com.structurizr.view.Shape.*

fun Stylize(workspace: Workspace) {

    val containerBackColor = "#2A4E6E"
    val componentBackColor = "#6CB33E"
    val componentFontColor = "#000000"
    val otherSystemBackColor = "#999999"
    val whiteColor = "#ffffff"
    val systemBackColor = "#1168bd"

    workspace.views.configuration.styles.apply {
        addElementStyle(PERSON).background("#77559E").color(whiteColor).shape(Person)
    }.apply {
        addElementStyle(E_SYSTEM_TAG.name).background(systemBackColor).color(whiteColor)
        addElementStyle(SOFTWARE_SYSTEM).background(systemBackColor).color(whiteColor)
        addElementStyle(OTHER_SYSTEMS_TAG.name).background(otherSystemBackColor).color(whiteColor)
    }.apply {
        addElementStyle(E_CONTAINER_TAG.name).background(containerBackColor).color(whiteColor)
        addElementStyle(CONTAINER).background(containerBackColor).color(whiteColor)
    }.apply {
        addElementStyle(E_COMPONENT_TAG.name).background(componentBackColor).color(componentFontColor)
        addElementStyle(COMPONENT).background(componentBackColor).color(componentFontColor)
    }.apply {
        addElementStyle(MOBILE_PHONE.name).shape(MobileDevicePortrait)
    }.apply {
        addElementStyle(WEB_BROWSER.name).shape(WebBrowser)
    }.apply {
        addElementStyle(DATABASE.name).shape(Cylinder)
    }.apply {
        addElementStyle(MESSAGE_BROKER.name).shape(Pipe)
    }.apply {
        addElementStyle(SPRING_REST_CONTROLLER.name).background("#D4F3C0").color("#000000")
        addElementStyle(SPRING_SERVICE.name).background(componentBackColor).color(componentFontColor)
        addElementStyle(SPRING_REPOSITORY.name).background("#95D46C").color("#000000")
    }
    addTagsAccordingToLocation(workspace)
}

private fun addTagsAccordingToLocation(workspace: Workspace) {
    workspace.model.softwareSystems.forEach {
        if (it.location == Location.External) {
            it.addTags(OTHER_SYSTEMS_TAG.name)
        }
    }
}

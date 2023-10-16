package com.anmi.c4.diagram.style

import com.anmi.c4.model.element.ATag.*
import com.structurizr.Workspace
import com.structurizr.model.Location
import com.structurizr.model.Tags.*
import com.structurizr.model.addNonStyledTags
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
        addElementStyle(SOFTWARE_SYSTEM).background(systemBackColor).color(whiteColor)
        addElementStyle(EXTERNAL_SYSTEM.label).background(otherSystemBackColor).color(whiteColor)
    }.apply {
        addElementStyle(CONTAINER).background(containerBackColor).color(whiteColor)
    }.apply {
        addElementStyle(COMPONENT).background(componentBackColor).color(componentFontColor)
    }.apply {
        addElementStyle(MOBILE_PHONE.label).shape(MobileDevicePortrait)
    }.apply {
        addElementStyle(WEB_BROWSER.label).shape(WebBrowser)
    }.apply {
        addElementStyle(DATABASE.label).shape(Cylinder)
    }.apply {
        addElementStyle(MESSAGE_BROKER.label).shape(Pipe)
    }.apply {
        addElementStyle(SPRING_REST_CONTROLLER.label).background("#D4F3C0").color(componentFontColor)
        addElementStyle(SPRING_SERVICE.label).background(componentBackColor).color(componentFontColor)
        addElementStyle(SPRING_REPOSITORY.label).background("#95D46C").color(componentFontColor)
    }
    addTagsAccordingToLocation(workspace)
}

private fun addTagsAccordingToLocation(workspace: Workspace) {
    workspace.model.softwareSystems.forEach {
        if (it.location == Location.External) {
            it.addNonStyledTags(EXTERNAL_SYSTEM)
        }
    }
}

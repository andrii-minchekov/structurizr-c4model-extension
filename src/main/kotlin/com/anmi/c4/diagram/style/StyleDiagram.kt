package com.anmi.c4.diagram.style

import com.anmi.c4.model.element.ITag.E_COMPONENT_TAG
import com.anmi.c4.model.element.ITag.E_CONTAINER_TAG
import com.anmi.c4.model.element.ITag.E_SYSTEM_TAG
import com.anmi.c4.model.element.ITag.SPRING_REPOSITORY
import com.anmi.c4.model.element.ITag.SPRING_REST_CONTROLLER
import com.anmi.c4.model.element.ITag.SPRING_SERVICE
import com.structurizr.Workspace
import com.structurizr.model.Tags.COMPONENT
import com.structurizr.model.Tags.CONTAINER
import com.structurizr.model.Tags.PERSON
import com.structurizr.model.Tags.SOFTWARE_SYSTEM
import com.structurizr.view.Shape

class Stylist(val workspace: Workspace) {

    private val containerBackColor = "#2A4E6E"
    private val componentBackColor = "#6CB33E"
    private val componentFontColor = "#000000"
    private val otherSystemBackColor = "#999999"
    private val whiteColor = "#ffffff"

    fun style() {
        workspace.views.configuration.styles.apply {
            addElementStyle(PERSON).background("#77559E").color(whiteColor).shape(Shape.Person)
        }.apply {
            addElementStyle(E_SYSTEM_TAG.name).background("#1168bd").color(whiteColor)
            addElementStyle(SOFTWARE_SYSTEM).background("#1168bd").color(whiteColor)
        }.apply {
            addElementStyle(E_CONTAINER_TAG.name).background(containerBackColor).color(whiteColor)
            addElementStyle(CONTAINER).background(containerBackColor).color(whiteColor)
        }.apply {
            addElementStyle(E_COMPONENT_TAG.name).background(componentBackColor).color(componentFontColor)
            addElementStyle(COMPONENT).background(componentBackColor).color(componentFontColor)
        }.apply {
            addElementStyle(SPRING_REST_CONTROLLER.name).background("#D4F3C0").color("#000000")
            addElementStyle(SPRING_SERVICE.name).background(componentBackColor).color(componentFontColor)
            addElementStyle(SPRING_REPOSITORY.name).background("#95D46C").color("#000000")
        }
    }
}

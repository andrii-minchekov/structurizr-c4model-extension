package com.structurizr.documentation

import com.structurizr.Workspace
import com.structurizr.model.Element
import com.structurizr.model.findElementByCanonicalName

internal fun Workspace.replaceDocumentationBy(doc: Documentation) {
    doc.sections.forEach {
        val element: Element? = it.element?.let { element: Element -> model.findElementByCanonicalName(element.canonicalName) }
        this.documentation.addSection(element, it.title, it.format, it.content)
    }
    doc.decisions.forEach {
        this.documentation.addDecision(it.element?.let { model.getSoftwareSystemWithName(it.name) }, it.id, it.date, it.title, it.status, it.format, it.content)
    }
    doc.images.forEach {
        this.documentation.addImage(it)
    }
}
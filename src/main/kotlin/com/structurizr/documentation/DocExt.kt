package com.structurizr.documentation

import com.structurizr.Workspace

internal fun Workspace.replaceDocumentationBy(doc: Documentation) {
    doc.sections.forEach {
        this.documentation.addSection(Section(it.format, it.content))
    }
    doc.decisions.forEach {
        val decision = Decision().apply {
            this.id = it.id
            this.title = it.title
            this.date = it.date
            this.status = it.status
            this.format = it.format
            this.content = it.content

        }
        this.documentation.addDecision(decision)
    }
    doc.images.forEach {
        this.documentation.addImage(it)
    }
}
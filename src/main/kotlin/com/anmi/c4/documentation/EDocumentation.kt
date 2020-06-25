package com.anmi.c4.documentation

import com.structurizr.Workspace
import com.structurizr.documentation.AutomaticDocumentationTemplate
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.ContainerView
import java.io.File
import java.net.URL


class EDocumentation(workspace: Workspace) {

    private val template = AutomaticDocumentationTemplate(workspace)

    init {
        addImages()
        workspace.model.softwareSystems.forEach { ss ->
            val containerView = workspace.views.containerViews.first { view -> view.softwareSystem.name == ss.name }
            doc(ss, containerView)
        }
    }

    private fun doc(system: SoftwareSystem, containerView: ContainerView) {
        val docRootURL: URL? = ClassLoader.getSystemResource("documentation/${containerView.key}")
        if (docRootURL != null && docRootURL.protocol == "file") {
            template.addSections(system, File(docRootURL.toURI()))
        }
    }

    private fun addImages() {
        val imageUrl = ClassLoader.getSystemResource("documentation/images")
        if (imageUrl != null && imageUrl.protocol == "file") {
            template.addImages(File(imageUrl.toURI()))
        }
    }
}
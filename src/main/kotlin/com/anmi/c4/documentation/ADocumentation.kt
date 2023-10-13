package com.anmi.c4.documentation

import com.structurizr.Workspace
import com.structurizr.documentation.AutomaticDocumentationTemplate
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.ContainerView
import java.io.File
import java.net.URL


private const val DOC_BASE_DIR = "documentation"

class ADocumentation(workspace: Workspace) {

    private val template = AutomaticDocumentationTemplate(workspace)

    init {
        workspace.model.softwareSystems.forEach { ss ->
            workspace.views.containerViews.firstOrNull() { view -> view.softwareSystem.name == ss.name }?.let {
                addImages("$DOC_BASE_DIR/${it.key}")
                doc(ss, it)
            }
        }
    }

    private fun doc(system: SoftwareSystem, containerView: ContainerView) {
        val docRootURL: URL? = ClassLoader.getSystemResource("$DOC_BASE_DIR/${containerView.key}")
        if (docRootURL != null && docRootURL.protocol == "file") {
            template.addSections(system, File(docRootURL.toURI()))
        }
    }

    private fun addImages(imagePathOfSystem: String) {
        val imageUrl = ClassLoader.getSystemResource(imagePathOfSystem)
        if (imageUrl != null && imageUrl.protocol == "file") {
            template.addImages(File(imageUrl.toURI()))
        }
    }
}
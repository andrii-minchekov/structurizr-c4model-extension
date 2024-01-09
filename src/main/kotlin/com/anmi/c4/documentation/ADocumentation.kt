package com.anmi.c4.documentation

import com.structurizr.Workspace
import com.structurizr.importer.documentation.DefaultDocumentationImporter
import com.structurizr.importer.documentation.DefaultImageImporter
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.ContainerView
import java.io.File
import java.net.URL


private const val DOC_BASE_DIR = "documentation"

class ADocumentation(workspace: Workspace) {

    init {
        workspace.model.softwareSystems.forEach { ss ->
            workspace.views.containerViews.firstOrNull() { view -> view.softwareSystem.name == ss.name }?.let {
                doc(ss, it)
            }
        }
    }

    private fun doc(system: SoftwareSystem, containerView: ContainerView) {
        val docRootURL: URL? = ClassLoader.getSystemResource("$DOC_BASE_DIR/${containerView.key}")
        if (docRootURL != null && docRootURL.protocol == "file") {
            val file = File(docRootURL.toURI())
            val documentationImporter = DefaultDocumentationImporter()
            documentationImporter.importDocumentation(system, file)
            val imageImporter = DefaultImageImporter()
            imageImporter.importDocumentation(system, File(file.absolutePath + File.separator + "images"))
        }
    }
}
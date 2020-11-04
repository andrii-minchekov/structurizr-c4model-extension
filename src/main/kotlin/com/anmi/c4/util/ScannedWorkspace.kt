package com.anmi.c4.util

import com.anmi.c4.IWorkspace
import com.anmi.c4.analysis.ComponentFinderParams
import com.anmi.c4.analysis.LocalPathToGitUrl
import com.anmi.c4.analysis.Packages
import com.anmi.c4.analysis.Sources
import com.anmi.c4.config.ConfigInstance
import com.anmi.c4.config.StructurizrFactory
import com.anmi.c4.model.element.ISystem
import com.anmi.c4.model.element.ITag
import com.structurizr.model.Location
import com.structurizr.model.SequentialIntegerIdGeneratorStrategy
import com.structurizr.model.addComponentsFrom
import com.structurizr.model.defaultComponentView
import com.structurizr.model.getSystem

object ScannedWorkspace {

    private val GENERATOR = SequentialIntegerIdGeneratorStrategy()
    private val config = ConfigInstance.TEST

    @JvmStatic
    @JvmOverloads
    fun scanComponentsToPlaygroundFrom(containerName: String = "Scratch-${(0..10).random()}", packagesWithComponents: Set<String>,
                                       javadocSourceDirs: Set<String>? = Sources().sourceDirs,
                                       localPathToGitUrl: LocalPathToGitUrl? = null) {
        val workspace = IWorkspace.createEmptyWorkspace(config)
        val container = workspace.model.getSystem(fakeSystem()).addContainer(containerName, "It's just a playground container", "")
        container.addComponentsFrom(ComponentFinderParams(Packages(packagesWithComponents), javadocSourceDirs?.let { Sources(javadocSourceDirs, localPathToGitUrl) }))
        workspace.defaultComponentView(container)
        StructurizrFactory.client(config).putWorkspace(config.workspaceId, workspace)
    }

    fun fakeSystem(): ISystem {
        return object : ISystem {
            override val location: Location
                get() = Location.Internal
            override val label: String
                get() = "Fake System"
            override val description: String
                get() = ""
            override val tag: Array<ITag>
                get() = arrayOf()
        }
    }
}
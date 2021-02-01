package com.anmi.c4.util

import com.anmi.c4.IWorkspace
import com.anmi.c4.analysis.*
import com.anmi.c4.config.*
import com.anmi.c4.model.element.*
import com.structurizr.model.*

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
            override val tags: Array<ITag>
                get() = emptyArray()
        }
    }
}
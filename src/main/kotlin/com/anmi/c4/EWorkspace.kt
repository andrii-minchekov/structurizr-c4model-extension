package com.anmi.c4

import com.anmi.c4.EWorkspace.Companion.GENERATOR
import com.anmi.c4.analysis.ComponentFinderParams
import com.anmi.c4.analysis.LocalPathToGitUrl
import com.anmi.c4.analysis.Packages
import com.anmi.c4.analysis.Sources
import com.anmi.c4.config.Config
import com.anmi.c4.config.StructurizrFactory
import com.anmi.c4.diagram.Diagram
import com.anmi.c4.diagram.style.Stylist
import com.anmi.c4.documentation.EDocumentation
import com.anmi.c4.model.element.ESoftwareSystem
import com.anmi.c4.model.element.ETag
import com.structurizr.Workspace
import com.structurizr.model.*
import com.structurizr.view.StaticView
import java.lang.reflect.Constructor

class EWorkspace(private val config: Config) {
    val workspace = initWorkspace(config).apply { this.id = config.workspaceId }
    val doc = EDocumentation(workspace)

    fun addDiagrams(diagrams: List<Diagram<StaticView>>) {
        diagrams.forEach {
            it.draw(workspace)
        }
    }

    companion object {
        val GENERATOR = SequentialIntegerIdGeneratorStrategy()
        private val config = Config.TEST
        private val workspace = EWorkspace(config).workspace

        @JvmStatic
        @JvmOverloads
        fun scanComponentsToPlaygroundFrom(containerName: String = "Scratch-${(0..10).random()}", packagesWithComponents: Set<String>, javadocSourceDirs: Set<String>? = Sources().sourceDirs,
                                           localPathToGitUrl: LocalPathToGitUrl? = null) {
            val container = workspace.model.getSystem(fakeSystem()).addContainer(containerName, "It's just a playground container", "")
            container.addComponentsFrom(ComponentFinderParams(Packages(packagesWithComponents), javadocSourceDirs?.let { Sources(javadocSourceDirs, localPathToGitUrl) }))
            workspace.defaultComponentView(container)
            StructurizrFactory.client(config).putWorkspace(config.workspaceId, workspace)
        }

        fun fakeSystem(): ESoftwareSystem {
            return object : ESoftwareSystem {
                override val location: Location
                    get() = Location.Internal
                override val label: String
                    get() = "Fake System"
                override val description: String
                    get() = ""
                override val tag: Array<ETag>
                    get() = arrayOf()
            }
        }
    }
}

fun initWorkspace(config: Config): Workspace {
    val workspace = Workspace("Enterprise Architecture - ${config.profileName}", "This is a Software Model of Enterprise.")
    workspace.model.setIdGenerator(GENERATOR)
    Stylist(workspace).style()
    return workspace
}


fun createModel(): Model {
    return try {
        val constructor: Constructor<*> = Model::class.java.getDeclaredConstructor()
        constructor.isAccessible = true
        constructor.newInstance() as Model
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}


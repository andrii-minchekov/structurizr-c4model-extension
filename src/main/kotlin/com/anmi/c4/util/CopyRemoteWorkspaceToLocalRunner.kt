package com.anmi.c4.util

import com.anmi.c4.config.Config
import com.anmi.c4.config.StructurizrFactory
import com.structurizr.Workspace
import com.structurizr.io.plantuml.StructurizrPlantUMLExporter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedWriter
import java.io.File
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


class CopyRemoteWorkspaceToLocalRunner {
    companion object {

        private val logger: Logger = LoggerFactory.getLogger(CopyRemoteWorkspaceToLocalRunner::class.java)

        @JvmStatic
        fun run(workspaceCfg: Config, destinationSubFolder: String = "src/main/resources/documentation") {
            copyFromRemoteToLocal(workspaceCfg, destinationSubFolder)
        }

        fun copyFromRemoteToLocal(config: Config, destinationDirPrefix: String, ) {
            val workspace = takeWorkspaceFromRemote(config)

            workspace.model.softwareSystems.forEach { ss ->
                workspace.views.containerViews.firstOrNull() { view -> view.softwareSystem.name == ss.name }?.let {
                    val targetDirPath = Files.createDirectories(Paths.get(projectRootDir(), destinationDirPrefix, it.key))
                    copyWorkspaceJsonToResourceDir(targetDirPath, config.archiveLocation, workspace.id)
                    exportAllViewsToPlantUmlFiles(workspace, targetDirPath)
                }
            }
        }

        private fun takeWorkspaceFromRemote(config: Config): Workspace {
            val structurizrClient = StructurizrFactory.client(config)
            val workspaceId = config.workspaceId
            if (structurizrClient.unlockWorkspace(workspaceId)) {
                logger.info("Workspace $workspaceId was unlocked")
            }
            val workspace = structurizrClient.getWorkspace(workspaceId)
            logger.info("C4 Cloud workspace $workspaceId was retrieved ${if (config.archiveLocation.isNotBlank()) "and saved to ${config.archiveLocation}" else ""}")
            return workspace
        }

        private fun exportAllViewsToPlantUmlFiles(workspace: Workspace, targetDirPath: Path) {
            val fileExt = "puml"
            val workspaceId = workspace.id
            val diagrams = StructurizrPlantUMLExporter().export(workspace)
            val outputPath = targetDirPath.toString()
            for (diagram in diagrams) {
                var file = File(outputPath, java.lang.String.format("%s-%s.%s", prefix(workspaceId), diagram.key, fileExt))
                writeToFile(file, diagram.definition)
                if (diagram.frames.isNotEmpty()) {
                    var index = 1
                    for (frame in diagram.frames) {
                        file = File(
                            outputPath,
                            java.lang.String.format("%s-%s-%s.%s", prefix(workspaceId), diagram.key, index, fileExt)
                        )
                        writeToFile(file, frame.definition)
                        index++
                    }
                }
            }
        }

        @Throws(Exception::class)
        private fun writeToFile(file: File, content: String) {
            logger.info(" - writing " + file.getCanonicalPath())
            val writer: BufferedWriter = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)
            writer.write(content)
            writer.flush()
            writer.close()
        }

        private fun copyWorkspaceJsonToResourceDir(targetDirPath: Path, sourceDirPath: String, workspaceId: Long) {
            val lastFile: File? = File(sourceDirPath)
                .listFiles { _, name -> name.startsWith("structurizr-$workspaceId") }?.maxOrNull() // Get the last file

            if (lastFile != null) {
                val targetFilePath = Path.of(targetDirPath.toString(), "structurizr-workspace.json")
                Files.copy(lastFile.toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING)
                logger.info("Structurizr workspace json copied to: $targetFilePath")
            }
        }

        private fun projectRootDir(): String {
            val rootURL: URL? = this::class.java.getResource("/")
            val rootDir = File(rootURL!!.path).parentFile.parentFile.parentFile.parentFile.path
            return rootDir
        }

        private fun prefix(workspaceId: Long): String {
            return if (workspaceId > 0) {
                "structurizr-$workspaceId"
            } else {
                "structurizr"
            }
        }
    }
}
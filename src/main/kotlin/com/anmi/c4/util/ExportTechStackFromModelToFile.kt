package com.anmi.c4.util

import com.structurizr.Workspace
import com.structurizr.model.Location
import java.nio.file.Path

class ExportTechStackFromModelToFile {
    companion object {
        fun export(workspace: Workspace, destinationSubDirPath: String = TARGET_SUB_DIR) {
            val containers = workspace.model.softwareSystems.first { s -> s.location == Location.Internal }.containers
            val content = " Tech stack: " + containers.map { c-> c.technology }.joinToString().split(",").toSet().sorted()

            val targetDirPath = FileUtils.projectRootDirPlusSubDir(destinationSubDirPath, workspace)
            FileUtils.writeToFile(Path.of(targetDirPath.toString(), "tech-stack.txt").toFile(), content)
        }
    }
}
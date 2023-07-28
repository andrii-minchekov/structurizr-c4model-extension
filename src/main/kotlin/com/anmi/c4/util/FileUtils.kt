package com.anmi.c4.util

import com.structurizr.Workspace
import com.structurizr.model.Location
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

const val TARGET_SUB_DIR = "src/main/resources/documentation"

class FileUtils {
    companion object {

        private val logger: Logger = LoggerFactory.getLogger(FileUtils::class.java)

        fun projectRootDirPlusSubDir(subDirPath: String = TARGET_SUB_DIR, workspace: Workspace): Path {
            return workspace.model.softwareSystems.first { ss -> ss.location.name == Location.Internal.name }.let { ss ->
                workspace.views.containerViews.first { view -> view.softwareSystem.name == ss.name }.let {
                    Files.createDirectories(Paths.get(projectRootDir(), subDirPath, it.key))
                }
            }
        }

        fun projectRootDir(): String {
            val rootURL: URL? = Thread.currentThread().contextClassLoader.getResource("")
            return File(rootURL!!.path).parentFile.parentFile.parentFile.parentFile.path
        }

        @Throws(Exception::class)
        fun writeToFile(file: File, content: String) {
            file.writeText(content)
            logger.info("Written content to file=" + file.getCanonicalPath())
        }
    }
}


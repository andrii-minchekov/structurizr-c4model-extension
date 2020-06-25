package com.anmi.c4.analysis

import com.structurizr.analysis.*
import org.springframework.context.annotation.Bean
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.BiPredicate
import java.util.stream.Collectors

data class ComponentFinderParams @JvmOverloads constructor(
        val javaPackages: Packages,
        val javaSources: Sources? = Sources(),
        val strategies: List<ComponentFinderStrategy> = listOf(
                ControllerAndServiceComponentFinderStrategy(supportingTypesStrategies = arrayOf(ReferencedTypesSupportingTypesStrategy(false))),
                StructurizrAnnotationsComponentFinderStrategy(ReferencedTypesSupportingTypesStrategy(false)),
                AnnotatedMethodComponentFinderStrategy(Bean::class.java))

)

data class Packages @JvmOverloads constructor(val includes: Set<String>, val excludes: Set<Regex> = emptySet())

data class Sources @JvmOverloads constructor(
        val sourceDirs: Set<String> = defaultSourceDirs(),
        val localPathToGitUrl: LocalPathToGitUrl? = null
) {

    companion object {
        @JvmStatic
        fun defaultSourceDirs(): Set<String> = Files.find(File(".").toPath(), 5, BiPredicate { path: Path, _ -> path.toString().contains("src/main/java") }).use {
            it.map(Path::toString).filter { path -> path.endsWith("/java") }.collect(Collectors.toSet())
        }
    }
}

data class LocalPathToGitUrl(val gitRepoLocalDir: File, val gitRepoUrl: String, val gitBranch: String)

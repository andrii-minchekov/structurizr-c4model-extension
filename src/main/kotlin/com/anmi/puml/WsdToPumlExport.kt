@file:JvmName("WsdToPumlExport")

package com.anmi.puml

import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.LinkedList

object WsdToPumlExport {

    private const val WSD_DIR: String = "src/test/resources"
    @JvmStatic
    fun main(args: Array<String>) {
        export(WSD_DIR)
    }

    fun export(wsdDir: String) {
        copyAllWsdFilesToPuml(wsdDir).forEach { file ->
            FileReader(file).use { reader ->
                val linesWithoutWsdSpec = LinkedList(reader.readText().substringAfterLast(">>>").lines()).apply {
                    this.removeAt(0)
                    addQuotation(this)
                }
                FileWriter(file).use {
                    it.write(linesWithoutWsdSpec.joinToString(separator = System.lineSeparator()))
                }
            }
        }
    }

    private fun addQuotation(lines: LinkedList<String>): List<String> {
        lines.addFirst("@startuml")
        lines.addLast("@enduml")
        for ((index, it) in lines.withIndex()) {
            val cmpSeparator = "->"
            if (it.contains(cmpSeparator, true)) {
                val cmpSeparatorIndex = it.indexOf(cmpSeparator)
                val descSeparator = ":"
                val descSeparatorIndex = it.indexOf(descSeparator)
                val resultLine = "\"${it.substring(0, cmpSeparatorIndex)}\"$cmpSeparator\"" +
                        "${it.substring(cmpSeparatorIndex + cmpSeparator.length, descSeparatorIndex)}\"$descSeparator${it.substring(descSeparatorIndex + 1, it.length)}"
                lines[index] = resultLine
            }
        }
        return lines
    }

    private fun copyAllWsdFilesToPuml(wsdDir: String): Iterable<File> {
        return findAllWsd(File(wsdDir)).run {
            this.map {
                it.copyTo(File(it.canonicalPath.substringBefore(".") + ".puml"), true)
            }
        }
    }

    private fun findAllWsd(file: File): Iterable<File> {
        return file.walkTopDown().filter { name: File -> name.canonicalPath.endsWith("wsd") }.asIterable()
    }
}


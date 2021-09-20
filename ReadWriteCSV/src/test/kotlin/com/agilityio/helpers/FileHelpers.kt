package com.agilityio.helpers

import com.agilityio.utils.FileUtils
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class FileHelpers {
    fun write(filePath: String, contents: String) {
        val file = FileUtils().create(filePath)
        var fileWriter: FileWriter? = null
        try {
            fileWriter = FileWriter(file)
            fileWriter.append(contents)
        } catch (e: Exception) {
            println("Writing CSV error!")
            e.printStackTrace()
        } finally {
            try {
                fileWriter!!.flush()
                fileWriter.close()
            } catch (e: IOException) {
                println("Flushing/closing error!")
                e.printStackTrace()
            }
        }
    }

    fun read(filePath: String): List<String> {
        var fileReader: BufferedReader? = null
        var lines: List<String> = listOf()

        try {
            fileReader = BufferedReader(FileReader(filePath))

            // Read CSV content file
            lines = fileReader.readLines()
        } catch (e: Exception) {
            println("Reading CSV Error!")
            e.printStackTrace()
        } finally {
            try {
                fileReader!!.close()
            } catch (e: IOException) {
                println("Closing fileReader Error!")
                e.printStackTrace()
            }
        }
        return lines
    }

    fun updateFile(filePath: String, lineIndex: Int, itemIndex: Int, value: String) {
        val lines: MutableList<String> = read(filePath).toMutableList()
        val lineUpdate = lines[lineIndex]
        val item = lineUpdate.split(", ").toMutableList()
        item[itemIndex] = value
        lines[lineIndex] = item.joinToString()

        val buildLines = StringBuilder()
        lines.forEach { buildLines.append(it).append("\n") }
        write(filePath, buildLines.toString())
    }
}
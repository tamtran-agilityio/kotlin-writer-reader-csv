package com.agilityio.csv

import com.agilityio.utils.FieldUtils
import com.agilityio.utils.FileUtils
import com.agilityio.utils.HeaderUtils
import com.agilityio.utils.LineUtils
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Implement read csv file
 */
class CsvReader {
    lateinit var columns: List<CsvField>
    var headers: List<String>? = null

    /**
     * Handle read field name and type of field in data model
     * @param filePath string path file
     * @return list T
     */
    inline fun <reified T> read(filePath: String): List<T>? {
        columns = FieldUtils().readerFieldForType<T>()

        val objectMapper = jacksonObjectMapper()

        try {
            // Override full permission of file
            FileUtils().setFullPermission(filePath)
            val streams = Files.newInputStream(Paths.get(filePath))
            val lines: MutableList<String> = mutableListOf()
            // Try with resource
             streams.buffered().reader().use { reader ->
                 reader.forEachLine { line ->
                     lines.add(line)
                 }
            }

            if (lines.first().isNotEmpty() && !ignoreHeader) {
                headers = HeaderUtils().read(lines.first())
            }

            // Remove first line
            lines.removeAt(1)
            // Read the file line by line starting from the second line
            // Read all line and convert same type of field
            val listItem: MutableList<HashMap<String, Any>> = LineUtils<String>()
                .readLines(lines, columns)
            // Convert map to list hash map to string
            val serialized = objectMapper.writeValueAsString(listItem)
            return objectMapper.readValue(serialized, object : TypeReference<List<T>>() {})
        } catch (e: IOException) {
            println("Reading CSV Error!")
            e.printStackTrace()
        } catch (e: JsonProcessingException) {
            println("Reading CSV Error!")
            e.printStackTrace()
        } catch (e: Exception) {
            println("Reading CSV Error!")
            e.printStackTrace()
        }

        return null
    }

    companion object {
        var ignoreHeader: Boolean = false

        @JvmStatic
        fun ignoreReadHeader() {
            this.ignoreHeader = true
        }
    }
}


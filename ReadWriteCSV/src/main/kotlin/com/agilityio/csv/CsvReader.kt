package com.agilityio.csv

import com.agilityio.utils.FieldHelpers
import com.agilityio.utils.FileUtils
import com.agilityio.utils.HeaderUtils
import com.agilityio.utils.LineUtils
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.stream.Stream

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
        columns = FieldHelpers().readerFieldForType<T>()

        var fileReader: BufferedReader? = null
        val objectMapper = jacksonObjectMapper()

        try {
            // Update permission file after read
            val file: File = FileUtils().get(filePath)
            fileReader = BufferedReader(FileReader(file))

            // Read CSV header
            val line: String = fileReader.readLine()

            if (line.isNotEmpty() && !ignoreHeader) {
                headers = HeaderUtils().read(line)
            }

            // Read the file line by line starting from the second line
            val lines: Stream<String> = fileReader.lines().skip(0)
            val listItem: MutableList<HashMap<String, Any>> = LineUtils<String>().readLines(lines, columns)
            val serialized = objectMapper.writeValueAsString(listItem)
            val jsonNode: JsonNode = objectMapper.readTree(serialized)
            return objectMapper.convertValue(
                jsonNode, object : TypeReference<List<T>>() {}
            )
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


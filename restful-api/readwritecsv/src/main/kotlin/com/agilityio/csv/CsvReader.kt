package com.agilityio.csv

import com.agilityio.utils.FieldUtils
import com.agilityio.utils.FileUtils
import com.agilityio.utils.HeaderUtils
import com.agilityio.utils.LineUtils
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sun.org.slf4j.internal.Logger
import com.sun.org.slf4j.internal.LoggerFactory
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Implement read csv file
 */
class CsvReader {
    val logger: Logger = LoggerFactory.getLogger(CsvReader::class.java)
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
            // Try with resource read file
            streams.buffered().reader().use { reader ->
                reader.forEachLine { line ->
                    lines.add(line)
                }
            }

            // Get header first line
            val header = lines.removeAt(0)

            if (header.isNotEmpty() && !ignoreHeader) {
                headers = HeaderUtils().read(header)
            }

            // Read the file line by line starting from the second line
            // Read all line and convert same type of field
            val listItem: MutableList<HashMap<String, Any>> = LineUtils<String>()
                .readLines(lines, columns, filePath)

            // Convert map to list hash map to string
            val serialized = objectMapper.writeValueAsString(listItem)
            return objectMapper.readValue(serialized, object : TypeReference<List<T>>() {})
        } catch (e: IOException) {
            logger.error("Input data object Error!")
        } catch (e: JsonProcessingException) {
            logger.error("Json format object Error!")
        } catch (e: Exception) {
            logger.error("Reading CSV Error!")
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
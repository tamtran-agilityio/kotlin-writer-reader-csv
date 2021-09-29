package com.agilityio.csv

import com.agilityio.utils.FileUtils
import com.sun.org.slf4j.internal.Logger
import com.sun.org.slf4j.internal.LoggerFactory
import java.io.FileWriter
import java.io.IOException

/**
 * Implement csv write file
 */
class CsvWriter<V> {
    private val logger: Logger = LoggerFactory.getLogger(CsvWriter::class.java)

    /**
     * Implement create csv file with file name
     * @param filePath path name of file
     * @param values list data object
     */
    fun write(filePath: String, values: List<V>, headers: List<String>?) {
        if (values.isEmpty()) throw IOException("Data object not exists")

        val file = FileUtils().create(filePath)
        try {
            val fileWriter = FileWriter(file)

            val contents: String = CsvContent.Builder<V>()
                .header(headers)
                .lines(values)
                .build()
                .toString()

            fileWriter.use { it.write(contents) }
        } catch (e: Exception) {
            logger.error("Writing CSV error!")
        }
    }
}
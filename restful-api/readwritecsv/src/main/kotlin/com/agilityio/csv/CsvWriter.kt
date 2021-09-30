package com.agilityio.csv

import com.agilityio.utils.FieldUtils
import com.agilityio.utils.FileUtils
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * Implement csv write file
 */
class CsvWriter<V> {
    val logger: Logger = LoggerFactory.getLogger(CsvWriter::class.java)

    /**
     * Implement create csv file with file name
     * @param filePath path name of file
     * @param values list data object
     */
    inline fun <reified T> write(file: File, values: List<V>, headers: List<String>?) {
        val fields: List<CsvField> = FieldUtils().readerFieldForType<T>()
        if (values.isEmpty()) throw IOException("Data object not exists")

        val fileUpdate = FileUtils().create(file.path)
        try {
            val fileWriter = FileWriter(fileUpdate)

            val contents: String = CsvContent.Builder<V>()
                .header(headers)
                .fields(fields)
                .lines(values)
                .build()
                .toString()

            fileWriter.use { it.write(contents) }
        } catch (e: Exception) {
            logger.error("Writing CSV error!")
        }
    }
}
package com.agilityio.csv

import com.agilityio.utils.FileUtils
import java.io.FileWriter
import java.io.IOException

/**
 * Implement csv write file
 */
class CsvWriter<V>(private val headers: List<String>?) {
    /**
     * Implement create csv file with file name
     * @param filePath path name of file
     * @param values list data object
     */
    fun write(filePath: String, values: List<V>) {
        if (values.isEmpty()) throw IOException("Data object not exists")

        val file = FileUtils().create(filePath)
        var fileWriter: FileWriter? = null
        try {
            fileWriter = FileWriter(file)

            val contents: String = CsvContents.Builder<V>()
                .header(headers)
                .lines(values)
                .build()
                .toString()

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
}
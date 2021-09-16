package com.agilityio.csv

import com.agilityio.utils.FieldHelpers
import com.agilityio.utils.HeaderUtils
import com.agilityio.utils.LineUtils
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 * Implement read csv file
 */
class CsvReader {
    lateinit var headers: List<String>
    lateinit var columns: List<CsvField>

    /**
     * Handle read field name and type of field in data model
     * @param filePath string path file
     * @return list T
     */
    inline fun <reified T> read(filePath: String): MutableList<T> {
        columns = FieldHelpers().readerFieldForType<T>()

        val values: MutableList<T> = mutableListOf()
        var fileReader: BufferedReader? = null
        val mapper = jacksonObjectMapper()

        try {
            var line: String?
            fileReader = BufferedReader(FileReader(filePath))

            // Read CSV header
            // Config reader boolean
            // Read header error
            line = fileReader.readLine()
            headers = HeaderUtils().read(line)

            // Read the file line by line starting from the second line
            line = fileReader.readLine()

            while (line != null) {
                if (line.isNotEmpty()) {
                    val fields: HashMap<String, Any> = LineUtils<String>().read(line, columns)
                    val serialized = mapper.writeValueAsString(fields)
                    val item: T = mapper.readValue(serialized)
                    values.add(item)
                }
                line = fileReader.readLine()
            }
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
        return values
    }
}


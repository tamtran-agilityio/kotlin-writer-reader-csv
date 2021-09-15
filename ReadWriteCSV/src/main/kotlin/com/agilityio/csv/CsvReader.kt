package com.agilityio.csv

import com.agilityio.product.Helpers
import com.agilityio.utils.FieldHelpers
import com.agilityio.utils.FormatObject
import com.agilityio.utils.HeaderUtils
import com.agilityio.utils.LineUtils
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import kotlin.reflect.KClass

/**
 * Implement read csv file
 * @param filePath file name of csv file
 */
class CsvReader<T>() : Csv<T> {
    override var values: MutableList<List<Any>> = mutableListOf()
    override var headers: List<String> = listOf()
    lateinit var columns: List<CsvField>

    /**
     * Handle get field name and type of field in data model
     */
    inline fun <reified T> getFieldAndType() {
        columns = FieldHelpers().readerFieldForType<T>()
    }

    override fun read(filePath: String) {
        var fileReader: BufferedReader? = null
        try {
            var line: String?
            fileReader = BufferedReader(FileReader(filePath))

            // Read CSV header
            line = fileReader.readLine()
            headers = HeaderUtils().read(line)

            // Read the file line by line starting from the second line
            line = fileReader.readLine()

            while (line != null) {
                if (line.isNotEmpty()) {
                    val fields = LineUtils<String>().read(line)
                    val lineFormat = columns.mapIndexed { index, csvField ->
                        when(csvField.type) {
                            Long::class -> Pair<String, Long>(csvField.name, Helpers().convertStringTo<Long>(fields[index]))
                            Double::class -> Pair<String, Double>(csvField.name, Helpers().convertStringTo<Double>(fields[index]))
                            String::class -> Pair<String, String>(csvField.name, Helpers().convertStringTo<String>(fields[index]))
                            Int::class -> Pair<String, Int>(csvField.name, Helpers().convertStringTo<Int>(fields[index]))
                            Boolean::class -> Pair<String, Boolean>(csvField.name, Helpers().convertStringTo<Boolean>(fields[index]))
                            else -> throw IllegalStateException("Unknown Generic Type")
                        }
                    }
                    values.add(lineFormat)
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
    }
}
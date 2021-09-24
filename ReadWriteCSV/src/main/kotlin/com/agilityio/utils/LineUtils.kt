package com.agilityio.utils

import com.agilityio.csv.CsvField
import com.agilityio.csv.CsvWriter
import com.agilityio.product.Helpers
import java.util.stream.Stream

/**
 * Implement helper read write line in Csv file
 */
class LineUtils<T> {
    /**
     * Implement read line of Csv file convert line to data object with type of each columns
     * @param line String text when read in Csv file
     * @param columns list field need read data
     * @return hashMap key value by type of field
     * TO DO: add code example to here
     */

    private fun read(line: String, columns: List<CsvField>): HashMap<String, Any> {
        val fieldMap = HashMap<String, Any>()
        val fields = line.split(", ")
        columns.forEachIndexed { index, csvField ->
            when (csvField.type) {
                Long::class ->
                    fieldMap[csvField.name] = Helpers().convertStringTo<Long>(fields[index])
                Double::class ->
                    fieldMap[csvField.name] = Helpers().convertStringTo<Double>(fields[index])
                String::class ->
                    fieldMap[csvField.name] = Helpers().convertStringTo<String>(fields[index])
                Int::class ->
                    fieldMap[csvField.name] = Helpers().convertStringTo<Int>(fields[index])
                Boolean::class ->
                    fieldMap[csvField.name] = Helpers().convertStringTo<Boolean>(fields[index])
                else -> throw IllegalStateException("Unknown Generic Type")
            }
        }
        return fieldMap
    }

    /**
     * Implement read line of Csv file
     * @param lines stream string when read in Csv file
     * @param columns list field need read data
     * @return list hashMap key value by type of field
     */
    fun readLines(lines: MutableList<String>, columns: List<CsvField>): MutableList<HashMap<String, Any>> {
        val lineErrors: MutableList<String> = mutableListOf()
        val lineConvertSuccess: MutableList<HashMap<String, Any>> = mutableListOf()

        // TO DO: line error include line number and column error
        // File name same file input
        lines.forEach { line ->
            run {
                try {
                    val fields: HashMap<String, Any> = read(line, columns)
                    lineConvertSuccess.add(fields)
                } catch (e: Exception) {
                    lineErrors.add(line + ", ${e.message}")
                }
            }
        }

        // Export field convert error
        if (lineErrors.isNotEmpty()) CsvWriter<String>().write(
            "filePath-error-${System.currentTimeMillis()}.csv",
            lineErrors,
            null
        )
        return lineConvertSuccess
    }

    /**
     * Implement builder string from data object convert data object to string
     *
     * @param data object need convert to string
     * @return String builder of line
     */
    fun write(data: T): StringBuilder {
        val delimitersNewLine = "\n"

        // Write object
        // Write error
        // Write error object // TO DO
        return when (data) {
            // Write string to line
            is String -> {
                StringBuilder().append(data).append(delimitersNewLine)
            }
            // Write object to line
            else -> {
                val valueString = FormatUtils<T>().toString(data)
                StringBuilder().append(valueString).append(delimitersNewLine)
            }
        }
    }
}
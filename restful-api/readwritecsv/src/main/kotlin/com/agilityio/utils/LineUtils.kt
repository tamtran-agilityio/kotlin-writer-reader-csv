package com.agilityio.utils

import com.agilityio.csv.CsvField
import com.agilityio.csv.CsvWriter
import org.slf4j.LoggerFactory
import org.slf4j.Logger

/**
 * Implement helper read write line in Csv file
 */
class LineUtils<T> {
    private val logger: Logger = LoggerFactory.getLogger(LineUtils::class.java)

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
            try {
                when (csvField.type) {
                    Long::class ->
                        fieldMap[csvField.name] = FieldUtils().convertStringTo<Long>(fields[index])
                    Double::class ->
                        fieldMap[csvField.name] = FieldUtils().convertStringTo<Double>(fields[index])
                    String::class ->
                        fieldMap[csvField.name] = FieldUtils().convertStringTo<String>(fields[index])
                    Int::class ->
                        fieldMap[csvField.name] = FieldUtils().convertStringTo<Int>(fields[index])
                    Boolean::class ->
                        fieldMap[csvField.name] = FieldUtils().convertStringTo<Boolean>(fields[index])
                    else -> throw IllegalStateException("Unknown generic type")
                }
            } catch (e: NumberFormatException) {
                throw NumberFormatException("${csvField.name} $e")
            } catch (e: IllegalStateException) {
                throw IllegalStateException("${csvField.name} $e")
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
    fun readLines(
        lines: MutableList<String>,
        columns: List<CsvField>,
        filePath: String
    ): MutableList<HashMap<String, Any>> {
        val lineErrors: MutableList<String> = mutableListOf()
        val lineConvertSuccess: MutableList<HashMap<String, Any>> = mutableListOf()

        // File name same file input
        lines.forEachIndexed { index, line ->
            run {
                try {
                    val fields: HashMap<String, Any> = read(line, columns)
                    lineConvertSuccess.add(fields)
                } catch (e: Exception) {
                    logger.error(e.message)
                    lineErrors.add(line + ", line: $index ${e.message}")
                }
            }
        }

        // Export field convert error
        if (lineErrors.isNotEmpty()) CsvWriter<String>().write(
            filePath,
            lineErrors,
            null
        )
        return lineConvertSuccess
    }

    /**
     * Implement builder string from data object convert data object to string
     * We can string or data object to string builder
     * @param data object need convert to string
     * @return String builder of data item
     */
    fun write(data: T): StringBuilder {

        val delimitersNewLine = "\n"

        return when (data) {
            // Build string to string builder
            is String -> {
                StringBuilder().append(data).append(delimitersNewLine)
            }
            // Build object to string builder
            else -> {
                val valueString = FormatUtils<T>().toString(data)
                StringBuilder().append(valueString).append(delimitersNewLine)
            }
        }
    }
}
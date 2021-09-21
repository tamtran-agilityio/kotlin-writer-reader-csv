package com.agilityio.utils

import com.agilityio.csv.CsvField
import com.agilityio.product.Helpers
import java.util.stream.Stream

/**
 * Implement helper read write line in Csv file
 */
class LineUtils<T> {
    private val delimitersNewLine: String = "\n"

    /**
     * Implement read line of Csv file
     * @param line String text when read in Csv file
     * @param columns list field need read data
     * @return hashMap key value by type of field
     */
    fun read(line: String, columns: List<CsvField>): HashMap<String, Any> {
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
    fun readLines(lines: Stream<String>, columns: List<CsvField>): MutableList<HashMap<String, Any>> {
        val lineErrors: MutableList<String> = mutableListOf()
        val lineConvertSuccess: MutableList<HashMap<String, Any>> = mutableListOf()

        lines.forEach { line ->
            run {
                try {
                    val fields: HashMap<String, Any> = read(line, columns)
                    lineConvertSuccess.add(fields)

                } catch (e: Exception) {
                    lineErrors.add(line)
                }
            }
        }

        return lineConvertSuccess
    }

    /**
     * Implement builder string from data object
     * @param data object need convert to string
     * @return String builder of line
     */
    fun write(data: T): StringBuilder {
        val formatObject = FormatObject<T>()
        val valueString = formatObject.toString(data)

        return StringBuilder().append(valueString).append(delimitersNewLine)
    }
}
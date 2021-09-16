package com.agilityio.utils

import com.agilityio.csv.CsvField
import com.agilityio.product.Helpers

class LineUtils<T> {
    private val delimitersNewLine: String = "\n"

    fun read(line: String, columns: List<CsvField>): HashMap<String, Any> {
        val fieldMap = HashMap<String, Any>()
        val fields = line.split(", ")
        // FIX ME: Current user class Instance of type ==> Unresolved reference: type
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

    fun write(line: T): StringBuilder {
        val formatObject = FormatObject<T>()
        val valueString = formatObject.toString(line)

        return StringBuilder().append(valueString).append(delimitersNewLine)
    }
}
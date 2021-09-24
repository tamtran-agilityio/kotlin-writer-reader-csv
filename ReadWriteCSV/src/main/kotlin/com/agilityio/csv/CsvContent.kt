package com.agilityio.csv

import com.agilityio.utils.HeaderUtils
import com.agilityio.utils.LineUtils

/**
 * Implement build content of Csv file
 */
class CsvContent {
    data class Builder<V>(
        var header: List<String>? = listOf(),
        var lines: List<V> = listOf()
    ) {
        fun header(header: List<String>?) = apply { this.header = header }
        fun lines(lines: List<V>) = apply { this.lines = lines }
        fun build() = run { buildTable(lines) }

        /**
         * Implement build header of csv file
         * @param header list string of fields
         * @return string builder of headers
         */
        private fun buildHeader(header: List<String>?): StringBuilder {
            val delimitersNewLine = "\n"
            return if (header == null) {
                StringBuilder().append(delimitersNewLine)
            } else {
                HeaderUtils().write(header)
            }
        }

        /**
         * Implement build line on table csv file
         * @param value object need to convert to string
         * @return string builder of line
         */
        private fun buildLine(value: V): StringBuilder {
            return LineUtils<V>().write(value)
        }

        /**
         * Implement build table string of csv file
         * @param values list objects need to convert to string
         * @return multiple line string builder of table csv
         */
        private fun buildTable(values: List<V>): StringBuilder {
            val stringBuffer = StringBuilder()

            // If header null not build header
            stringBuffer.append(buildHeader(header).toString())
            // Build content of file
            values.stream().forEach { stringBuffer.append(buildLine(it)) }

            return stringBuffer
        }
    }
}
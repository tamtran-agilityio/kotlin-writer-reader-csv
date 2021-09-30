package com.agilityio.utils

import com.agilityio.csv.CsvField
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * Implement format object
 */
class FormatUtils<T> {
    /**
     * Implement convert object to string
     * @param value: object need to convert
     * @return string values from object
     */
    fun toString(value: T, fields: List<CsvField>): String {
        val values: MutableList<String> = mutableListOf()
//        val mapper = jacksonObjectMapper()
//        val serialized = mapper.writeValueAsString(value)
//        val mapLine: Map<String, Any> = mapper.readValue(serialized)
//        return mapLine.values.joinToString()
        return values.joinToString()
    }
}
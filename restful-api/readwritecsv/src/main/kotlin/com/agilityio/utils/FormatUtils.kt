package com.agilityio.utils

import com.agilityio.csv.CsvField
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.reflect.full.memberProperties

data class GenreDTO(val id: String, val name: Any)

/**
 * Implement format object
 */
class FormatUtils<T> {
    /**
     * Implement convert object to string
     * @param value: object need to convert
     * @return string values from object
     */
    fun toString(value: T): String {
        val mapper = jacksonObjectMapper()
        val serialized = mapper.writeValueAsString(value)
        val mapLine: Map<*, *> = mapper.readValue(serialized, Map::class.java)
        return mapLine.values.joinToString()
    }
}
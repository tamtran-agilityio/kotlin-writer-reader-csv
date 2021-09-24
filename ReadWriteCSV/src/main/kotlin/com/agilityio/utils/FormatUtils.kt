package com.agilityio.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Implement format object
 */
class FormatUtils<T> : Format<T> {
    /**
     * Implement object to string
     * @param value: object need to convert
     * @return string values from object
     */
    override fun toString(value: T): String {
        val mapper = jacksonObjectMapper()
        val serialized = mapper.writeValueAsString(value)
        val mapLine: Map<String, Any> = mapper.readValue(serialized)
        return mapLine.values.joinToString()
    }
}
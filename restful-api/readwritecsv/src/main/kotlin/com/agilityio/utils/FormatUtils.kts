package com.agilityio.utils

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
//        val mapper = jacksonObjectMapper()
//        val serialized = mapper.writeValueAsString(value)
//        val mapLine: Map<String, Any> = mapper.readValue(serialized)
//        return mapLine.values.joinToString()
        return "null"
    }
}
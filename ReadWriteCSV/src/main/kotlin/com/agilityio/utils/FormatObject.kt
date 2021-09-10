package com.agilityio.utils

/**
 * Implement format object
 */
class FormatObject<V> : Format<V> {
    /**
     * Implement object to string
     * @param value: object need to convert
     * @return string values from object
     */
    override fun toString(value: V): String {
        return value.toString()
            .replace("null", " ")
            .substringAfter('(')
            .substringBeforeLast(')')
            .split(", ")
            .map { with(it.split("=")) { this[0] to this[1]} }
            .joinToString { it.second }

    }

    override fun toObject(string: V): Object {
        TODO("Not yet implemented")
    }
}
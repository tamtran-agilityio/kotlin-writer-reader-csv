package com.agilityio.utils

/**
 * Implement format object
 */
class FormatObject<T> : Format<T> {
    /**
     * Implement object to string
     * @param value: object need to convert
     * @return string values from object
     */
    override fun toString(value: T): String {
        return value.toString()
            .replace("null", " ")
            .substringAfter('(')
            .substringBeforeLast(')')
            .split(", ")
            .map { with(it.split("=")) { this[0] to this[1]} }
            .joinToString { it.second }

    }
}
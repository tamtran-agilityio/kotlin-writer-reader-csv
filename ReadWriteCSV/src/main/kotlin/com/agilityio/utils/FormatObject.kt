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

    /**
     * Implement convert to type of file
     * FIX ME: ***get class type of type***
     */
    override fun convertToType(type: Class<*>, value: String): T {
        return when(type) {

            is Comparable<*> -> value.toDouble() as T
//            "String" -> value as T
//            "Long" -> value.toLong() as T
//            "Int" -> value.toInt() as T
//            "Boolean" -> value.toBoolean() as T
            // add other types here if you need
            else -> throw IllegalStateException("Unknown Generic Type")
        }
    }
}
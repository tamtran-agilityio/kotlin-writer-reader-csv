package com.agilityio.utils

/**
 * Interface format
 * V type of value
 */
interface Format<V> {
    /**
     * Implement convert value to string
     */
    fun toString(value: V): String

    /**
     * Implement convert value to Object
     */
    fun toObject(string: V): Object
}
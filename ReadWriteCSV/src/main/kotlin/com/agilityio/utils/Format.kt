package com.agilityio.utils

/**
 * Interface format
 * V type of value
 */
interface Format<T> {
    /**
     * Implement convert value to string
     */
    fun toString(value: T): String
}
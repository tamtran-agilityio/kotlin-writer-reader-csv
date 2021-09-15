package com.agilityio.csv

import com.agilityio.product.Product

/**
 * Interface of csv
 * wrapper
 */
interface Csv<T> {
    val headers: List<String>
    val values: MutableList<List<Any>>

    /**
     * Implement read context on csv file
     */
    fun read(filePath: String)
}
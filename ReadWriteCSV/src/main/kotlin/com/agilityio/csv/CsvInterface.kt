package com.agilityio.csv

import com.agilityio.product.Product

/**
 * Interface of csv
 */
interface CsvInterface<V> {
    val headers: List<String>
    val values: MutableList<V>

    /**
     * Implement read context on csv file
     */
    fun get()
}
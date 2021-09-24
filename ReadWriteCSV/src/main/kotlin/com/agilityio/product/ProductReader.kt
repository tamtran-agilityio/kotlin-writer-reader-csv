package com.agilityio.product

import com.agilityio.csv.CsvReader
import com.agilityio.utils.FieldUtils

typealias Helpers = FieldUtils
/**
 * Implement read product from Csv file
 */
class ProductCsvReader(filePath: String) {
    private var csvRead: CsvReader = CsvReader()
    private var products: List<Product>?

    // Initializer blocks prefixed
    init {
        CsvReader.ignoreReadHeader()
        products = csvRead.read(filePath)
    }

    /**
     * Implement get header of file Csv file
     * @return list string header of csv file
     */
    fun getHeader(): List<String>? {
        return csvRead.headers
    }

    /**
     * Implement convert content of table in Csv file to Product
     * @return list products in Csv file
     */
    fun getProducts(): List<Product>? {
        return products
    }
}

fun main() {
    val products = ProductCsvReader("products.csv")
    val headers = products.getHeader()
    val listProduct = products.getProducts()
    println("headers $headers")
    println("listProduct $listProduct")
}

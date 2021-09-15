package com.agilityio.product

import com.agilityio.csv.CsvReader
import com.agilityio.utils.FieldHelpers

typealias Helpers = FieldHelpers

/**
 * Implement read product from Csv file
 */
class ProductCsvReader<T>(filePath: String) {
    // Columns include id, price, ...

    private var csvRead: CsvReader<Product> = CsvReader()
    private val products: MutableList<Product> = mutableListOf()

    // Initializer blocks prefixed
    init {
        csvRead.getFieldAndType<Product>()
//        println(csvRead.columns)
        csvRead.read(filePath)
        println(csvRead.values)
    }

    /**
     * Implement get header of file Csv file
     * @return list string header of csv file
     */
    fun getHeader(): List<String> {
        return csvRead.headers
    }

    /**
     * Implement convert content of table in Csv file to Product
     * @return list products in Csv file
     */
    fun getProducts(): MutableList<Product> {
        csvRead.values.forEach { line ->
            line.stream().forEach { it ->
                println(it)
            }

        // val product = Product()
        // product.id = readCell(line, "id")

        }
        return products
    }


    fun readCell(line: String, fieldName: String) {
        // TO DO read cell
    }
}


fun main() {
    val products = ProductCsvReader<Product>("products.csv")

    println(products.getProducts())
}

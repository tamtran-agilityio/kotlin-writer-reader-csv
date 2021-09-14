package com.agilityio.product

import com.agilityio.csv.CsvRead
import com.agilityio.utils.FieldHelpers

typealias Helpers = FieldHelpers

class ProductCsv(fileName: String) {
    private var csvRead: CsvRead<List<String>> = CsvRead(fileName)
    private val products: MutableList<Product> = mutableListOf()

    init {
        csvRead.get()
    }

    fun getHeader(): List<String> {
        return csvRead.headers
    }

    fun getProducts(): MutableList<Product> {
        val values = csvRead.values
        values.stream().forEach {
            val product = Product(
                Helpers().convertStringTo<Long>(it[0]),
                Helpers().convertStringTo<Double>(it[1]),
                Helpers().convertStringTo<String>(it[2]),
                Helpers().convertStringTo<String>(it[3]),
                Helpers().convertStringTo<String>(it[4]),
                Helpers().convertStringTo<String>(it[5]),
                Helpers().convertStringTo<String>(it[6]),
                Helpers().convertStringTo<Double>(it[7]),
                Helpers().convertStringTo<Double>(it[8]),
                Helpers().convertStringTo<String>(it[9]),
                Helpers().convertStringTo<Int>(it[10]),
                Helpers().convertStringTo<String>(it[11]),
                Helpers().convertStringTo<Long>(it[12]),
                Helpers().convertStringTo<String>(it[13]),
                Helpers().convertStringTo<String>(it[14]),
                Helpers().convertStringTo<String>(it[15]),
                Helpers().convertStringTo<String>(it[16]),
                Helpers().convertStringTo<Long>(it[17]),
                Helpers().convertStringTo<String>(it[18]),
                Helpers().convertStringTo<Boolean>(it[19]),
                Helpers().convertStringTo<String>(it[20]),
                Helpers().convertStringTo<Double>(it[21]),
                Helpers().convertStringTo<Double>(it[22]),
                Helpers().convertStringTo<String>(it[23]),
                Helpers().convertStringTo<String>(it[24]),
                Helpers().convertStringTo<String>(it[25]),
                Helpers().convertStringTo<Double>(it[26])
            )
            products.add(
                product
            )
        }
        return products
    }
}

fun main() {
    val products = ProductCsv("products.csv")
    println(products.getHeader())
    println(products.getProducts())
}
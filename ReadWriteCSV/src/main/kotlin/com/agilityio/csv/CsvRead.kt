package com.agilityio.csv

import com.agilityio.product.Product
import com.agilityio.utils.FieldHelpers
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 * Implement read csv file
 * @param fileName file name of csv file
 */
class CsvRead(private val fileName: String) {
    /**
     * Implement read csv file
     */
    fun read() {
        var fileReader: BufferedReader? = null
        var fieldHelpers = FieldHelpers()

        try {
            val products = mutableListOf<Product>()
            var line: String?

            fileReader = BufferedReader(FileReader(fileName))

            // Read CSV header
            val headers = fileReader.readLine()

            // Read the file line by line starting from the second line
            line = fileReader.readLine()
            while (line != null) {
                val fields = line.split(", ")
                if (fields.isNotEmpty()) {
                    val product = Product(
                        fieldHelpers.convertStringTo<Long>(fields[0]),
                        fieldHelpers.convertStringTo<Double>(fields[1]),
                        fieldHelpers.convertStringTo<String>(fields[2]),
                        fieldHelpers.convertStringTo<String>(fields[3]),
                        fieldHelpers.convertStringTo<String>(fields[4]),
                        fieldHelpers.convertStringTo<String>(fields[5]),
                        fieldHelpers.convertStringTo<String>(fields[6]),
                        fieldHelpers.convertStringTo<Double>(fields[7]),
                        fieldHelpers.convertStringTo<Double>(fields[8]),
                        fieldHelpers.convertStringTo<String>(fields[9]),
                        fieldHelpers.convertStringTo<Int>(fields[10]),
                        fieldHelpers.convertStringTo<String>(fields[11]),
                        fieldHelpers.convertStringTo<Long>(fields[12]),
                        fieldHelpers.convertStringTo<String>(fields[13]),
                        fieldHelpers.convertStringTo<String>(fields[14]),
                        fieldHelpers.convertStringTo<String>(fields[15]),
                        fieldHelpers.convertStringTo<String>(fields[16]),
                        fieldHelpers.convertStringTo<Long>(fields[17]),
                        fieldHelpers.convertStringTo<String>(fields[18]),
                        fieldHelpers.convertStringTo<Boolean>(fields[19]),
                        fieldHelpers.convertStringTo<String>(fields[20]),
                        fieldHelpers.convertStringTo<Double>(fields[21]),
                        fieldHelpers.convertStringTo<Double>(fields[22]),
                        fieldHelpers.convertStringTo<String>(fields[23]),
                        fieldHelpers.convertStringTo<String>(fields[24]),
                        fieldHelpers.convertStringTo<String>(fields[25]),
                        fieldHelpers.convertStringTo<Double>(fields[26])
                    )
                    products.add(product)
                }

                line = fileReader.readLine()
            }

            // Print the new product list
            for (product in products) {
                println(product)
            }
        } catch (e: Exception) {
            println("Reading CSV Error!")
            e.printStackTrace()
        } finally {
            try {
                fileReader!!.close()
            } catch (e: IOException) {
                println("Closing fileReader Error!")
                e.printStackTrace()
            }
        }
    }

}

fun main() {
    val csvRead = CsvRead("products.csv")
    csvRead.read()
}
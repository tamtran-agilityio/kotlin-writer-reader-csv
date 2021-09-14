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
class CsvRead<V>(val fileName: String): CsvInterface<V> {
    override var values: MutableList<V> = mutableListOf()
    override var headers: List<String> = listOf()

    /**
     * Implement read csv file
     */
    override fun get() {
        var fileReader: BufferedReader? = null

        try {
            var line: String?
            fileReader = BufferedReader(FileReader(fileName))

            // Read CSV header
            headers = fileReader.readLine().split(", ")

            // Read the file line by line starting from the second line
            line = fileReader.readLine()

            while (line != null) {
                val fields = line.split(", ")
                if (fields.isNotEmpty()) {
                    values.add(fields as V)
                }
                line = fileReader.readLine()
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
package com.agilityio.csv

import com.agilityio.product.Product
import com.agilityio.utils.FieldHelpers
import com.agilityio.utils.FileUtils
import com.agilityio.utils.HeaderUtils
import com.agilityio.utils.LineUtils
import java.io.FileWriter
import java.io.IOException

/**
 * Implement csv write file
 */
class CsvWriter<V>(private val headers: List<String>?) {
    /**
     * Implement build header of csv file
     * @param header list string of fields
     * @return string builder of headers
     */
    private fun buildHeader(header: List<String>?): StringBuilder {
        val delimitersNewLine = "\n"
        return if (header == null) {
            StringBuilder().append(delimitersNewLine)
        } else {
            HeaderUtils().write(header)
        }
    }

    /**
     * Implement build line on table csv file
     * @param value object need to convert to string
     * @return string builder of line
     */
    private fun buildLine(value: V): StringBuilder {
        return LineUtils<V>().write(value)
    }

    /**
     * Implement build table string of csv file
     * @param values list objects need to convert to string
     * @return multiple line string builder of table csv
     */
    private fun buildTable(values: List<V>): StringBuilder {
        val stringBuffer = StringBuilder()

        // If header null not build header
        stringBuffer.append(buildHeader(headers).toString())
        // Build content of file
        values.stream().forEach { stringBuffer.append(buildLine(it)) }

        return stringBuffer
    }

    /**
     * Implement create csv file with file name
     * @param filePath path name of file
     * @param values list data object
     */
    fun write(filePath: String, values: List<V>) {
        if (values.isEmpty()) throw IOException("Data object not exists")

        val file = FileUtils().create(filePath)
        var fileWriter: FileWriter? = null
        try {
            fileWriter = FileWriter(file)

            val contents: String = buildTable(values).toString()
            fileWriter.append(contents)
        } catch (e: Exception) {
            println("Writing CSV error!")
            e.printStackTrace()
        } finally {
            try {
                fileWriter!!.flush()
                fileWriter.close()
            } catch (e: IOException) {
                println("Flushing/closing error!")
                e.printStackTrace()
            }
        }
    }
}

fun main() {
    val headers: List<String> = FieldHelpers().getAllModelFieldsName(Product::class.java)
    val products = mutableListOf<Product>()
    val product = Product(
        1,
        21.0,
        "Phone",
        "Box",
        "K233234",
        "../image.png",
        "Image",
        1.0,
        210.0,
        "Box",
        1,
        "Box",
        2423,
        "Iphone",
        "Apple mobile",
        "Test",
        "Active",
        2,
        "Test",
        true,
        "Kg",
        0.1,
        0.1,
        "cm",
        "USD",
        "$",
        20.0
    )
    val product2 = Product(
        2,
        22.0,
        "Phone",
        "Box",
        "K233234",
        "../image.png",
        "Image",
        1.0,
        210.0,
        "Box",
        1,
        "Box",
        2423,
        "Iphone",
        "Apple mobile",
        "Test",
        "Active",
        2,
        "Test",
        true,
        "Kg",
        0.1,
        0.1,
        "cm",
        "USD",
        "$",
        20.0
    )

    products.add(product)
    products.add(product2)

    CsvWriter<Product>(headers).write("products.csv", products)
}
package com.agilityio.csv

import com.agilityio.product.Product
import com.agilityio.utils.FieldHelpers
import com.agilityio.utils.FormatObject
import com.agilityio.utils.HeaderUtils
import com.agilityio.utils.LineUtils
import java.io.FileWriter
import java.io.IOException

/**
 * Implement csv write file
 */
class CsvWrite<V>(private val headers: List<String>)  {


    /**
     * Implement build header of csv file
     * @param header list string of fields
     * @return string builder of headers
     */
    private fun buildHeader(header: List<String>): StringBuilder {
        return HeaderUtils().write(header)
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

        stringBuffer.append(buildHeader(headers))
        values.stream().forEach { stringBuffer.append(buildLine(it)) }

        return stringBuffer
    }

    /**
     * Implement create csv file with file name
     */
    fun write(filePath: String, values: List<V>) {
        val file = CsvFile().create(filePath)
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
    products.add(
        Product(
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
    )

    CsvWrite<Product>(headers).write("products.csv", products)
}
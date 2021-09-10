package com.agilityio.csv

import com.agilityio.product.Product
import com.agilityio.utils.FormatObject
import java.io.FileWriter
import java.io.IOException

/**
 * Implement csv write file
 */
class CsvWrite<V>(private val headers: List<String>?)  {

    fun buildHeader(headers: List<String>, value: V): StringBuilder {
        // FIX ME: if headers empty get fields value create header
        //        val fields = Product::class.java.declaredFields
        //        val t = fields.iterator()
        //        while (t.hasNext()) {
        //            println(t.next().name)
        //        }
        return StringBuilder().append(headers.joinToString()).append("\n")
    }

    private fun buildLine(value: V): StringBuilder {
        val formatObject = FormatObject<V>()
        val valueString = formatObject.toString(value)

        return StringBuilder().append(valueString).append("\n")
    }

    private fun buildTable(values: List<V>): StringBuilder {
        val stringBuffer = StringBuilder()

        stringBuffer.append(headers?.joinToString()).append("\n")
        values.stream().forEach { stringBuffer.append(buildLine(it)) }

        return stringBuffer
    }

    fun createCsvFile(nameFile: String, values: List<V>) {
        val file = CsvFile().create(nameFile)
        var fileWriter: FileWriter? = null
        try {
            fileWriter = FileWriter(file)
            val contents: String = buildTable(values).toString()
            println("contents $contents")
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
    val products = listOf<Product>()
    val headers = listOf<String>("")
    val fields = Product::class.java.declaredFields
    val t = fields.iterator()
    while (t.hasNext()) {
        println(t.next().name)
    }
//    while (t)
//    CsvWrite<Product>(headers).createCsvFile("products.csv", products)

}
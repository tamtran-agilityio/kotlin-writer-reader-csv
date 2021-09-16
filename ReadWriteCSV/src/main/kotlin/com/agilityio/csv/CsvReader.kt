package com.agilityio.csv

import com.agilityio.product.Helpers
import com.agilityio.product.Product
import com.agilityio.utils.FieldHelpers
import com.agilityio.utils.FormatObject
import com.agilityio.utils.HeaderUtils
import com.agilityio.utils.LineUtils
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import kotlin.reflect.KClass

/**
 * Implement read csv file
 * @param filePath file name of csv file
 */
class CsvReader<T>() {
    lateinit var headers: List<String>
    lateinit var columns: List<CsvField>

    /**
     * Handle read field name and type of field in data model
     */
    inline fun <reified T> read(filePath: String): MutableList<T> {
        columns = FieldHelpers().readerFieldForType<T>()

        var values: MutableList<T> = mutableListOf()
        var fileReader: BufferedReader? = null
        val gson = Gson()

        try {
            var line: String?
            fileReader = BufferedReader(FileReader(filePath))

            // Read CSV header
            line = fileReader.readLine()
            headers = HeaderUtils().read(line)

            // Read the file line by line starting from the second line
            line = fileReader.readLine()

            while (line != null) {
                if (line.isNotEmpty()) {
                    val fields = LineUtils<String>().read(line, columns)

                    val stringValue = gson.toJson(fields).toString()
                    // FIX ME: Cannot use 'T' as reified type parameter. Use a class instead.
                    var dataItem = gson.fromJson(stringValue, T::class.java)
                    values.add(dataItem)
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
        return values
    }
}


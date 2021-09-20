package com.agilityio.csv

import com.agilityio.helpers.FileHelpers
import com.agilityio.helpers.Mock
import com.agilityio.product.Product
import com.agilityio.utils.FieldHelpers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File


internal class CsvReaderTest {

    private val headers: List<String> = FieldHelpers().getAllModelFieldsName(Product::class.java)
    private lateinit var products: MutableList<Product>
    private val filePath: String = "test.csv"
    private var csvRead: CsvReader = CsvReader()

    @BeforeEach
    fun setUp() {
        products = Mock().products(10, 1, 10)
    }

    @AfterEach
    fun tearDown() {
        val file = File("test.csv")
        file.deleteRecursively()
    }

    @Test
    fun readSuccessWithSingleLineNotHeader() {
        CsvWriter<Product>(null).write(filePath, products)

        val resProducts: MutableList<Product> = csvRead.read(filePath)
        assertEquals(products, resProducts)
//        assertTrue(csvRead.headers.size())
    }

    @Test
    fun readSuccessWithSingleLineIncludeHeader() {
        CsvWriter<Product>(headers).write(filePath, products)

        val resProducts: MutableList<Product> = csvRead.read(filePath)
        assertEquals(products, resProducts)
        assertEquals(headers, csvRead.headers)
    }

    @Test
    fun readSuccessWithMultipleLineNotHeader() {
        products = Mock().products(10, 1, 100)

        CsvWriter<Product>(null).write(filePath, products)

        val resProducts: MutableList<Product> = csvRead.read(filePath)
        assertEquals(products, resProducts)
//        assertTrue(csvRead.headers.size())
    }

    @Test
    fun readSuccessWithMultipleLineIncludeHeader() {
        products = Mock().products(10, 1, 100)

        CsvWriter<Product>(headers).write(filePath, products)

        val resProducts: MutableList<Product> = csvRead.read(filePath)
        assertEquals(products, resProducts)
        assertEquals(headers, csvRead.headers)
    }

    @Test
    fun readErrorWithMultipleLineIncludeHeader() {
        products = Mock().products(10, 1, 100)

        CsvWriter<Product>(headers).write(filePath, products)
        FileHelpers().updateFile(filePath, 2, 1, "Test")

        try {
            csvRead.read<Product>(filePath)
        } catch (e: Exception) {
            e.printStackTrace()
            assertEquals(NumberFormatException::class.java, e)
        }
    }


}
package com.agilityio.csv

import com.agilityio.helpers.Mock
import com.agilityio.product.Product
import com.agilityio.utils.FieldHelpers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

internal class CsvWriterTest {

    private val headers: List<String> = FieldHelpers().getAllModelFieldsName(Product::class.java)
    private lateinit var products: MutableList<Product>
    private val filePath: String = "test.csv"

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
    // Test write csv success not include header
    fun writeCsvSuccessIgnoreHeaderWithOneLine() {
        products = Mock().products(1, 1, 10)

        CsvWriter<Product>(null).write(filePath, products)
        val file = File(filePath)
        val count = BufferedReader(FileReader(file)).lines().count()

        assertTrue(file.exists())
        assertEquals(1, count)
    }

    @Test
    // Test write csv success include header
    fun writeCsvSuccessWithOneLine() {
        products = Mock().products(1, 1, 10)

        CsvWriter<Product>(headers).write(filePath, products)
        val file = File(filePath)
        val count = BufferedReader(FileReader(file)).lines().count()

        assertTrue(file.exists())
        assertEquals(2, count)
    }

    @Test
    // Test write csv success include header
    fun writeCsvErrorWithOneLine() {
        products = mutableListOf()
        CsvWriter<Product>(null)
        val csvWriter =CsvWriter<Product>(null)
        val exception: Throwable =
            assertThrows(IOException::class.java) { csvWriter.write(filePath, products) }
        assertEquals("Data object not exists", exception.message)
    }

    @Test
    // Test write csv success not include header
    fun writeCsvSuccessIgnoreHeaderWithMultiplesLine() {
        products = Mock().products(10, 1, 10)

        CsvWriter<Product>(null).write(filePath, products)
        val file = File(filePath)
        val count = BufferedReader(FileReader(file)).lines().count()

        assertTrue(file.exists())
        assertEquals(10, count)
    }

    @Test
    // Test write csv success not include header
    fun writeCsvSuccessWithMultiplesLine() {
        products = Mock().products(10, 1, 10)

        CsvWriter<Product>(headers).write(filePath, products)
        val file = File(filePath)
        val count = BufferedReader(FileReader(file)).lines().count()

        assertTrue(file.exists())
        assertEquals(11, count)
    }
}
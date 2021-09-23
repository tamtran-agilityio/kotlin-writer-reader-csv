package com.agilityio.csv

import com.agilityio.helpers.FileHelpers
import com.agilityio.helpers.Mock
import com.agilityio.product.Product
import com.agilityio.utils.FieldHelpers
import com.agilityio.utils.FileUtils
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.attribute.PosixFilePermission

internal class CsvReaderTest {

    private val headers: List<String> = FieldHelpers().getAllModelFieldsName(Product::class.java)
    private lateinit var products: List<Product>
    private val filePath: String = "test.csv"
    private var csvRead: CsvReader = CsvReader()

    @BeforeEach
    fun setUp() {
        products = Mock().products(1, 1, 10)
    }

    @AfterEach
    fun tearDown() {
        val file = File("test.csv")
        file.deleteRecursively()
    }

    @Test
    // Test read success with single line not header
    fun readSuccessWithSingleLineNotHeader() {
        CsvWriter<Product>().write(filePath, products, null)
        val resProducts: List<Product>? = csvRead.read(filePath)

        assertEquals(products, resProducts)
        assertNull(csvRead.headers)
    }

    @Test
    // Test read success with single line include header
    fun readSuccessWithSingleLineIncludeHeader() {
        CsvWriter<Product>().write(filePath, products, headers)
        val resProducts: List<Product>? = csvRead.read(filePath)

        assertEquals(products, resProducts)
        assertEquals(headers, csvRead.headers)
    }

    @Test
    // Test read success with multiple line not header
    fun readSuccessWithMultipleLineNotHeader() {
        products = Mock().products(10, 1, 100)
        CsvWriter<Product>().write(filePath, products, null)
        val resProducts: List<Product>? = csvRead.read(filePath)

        assertEquals(products, resProducts)
        assertNull(csvRead.headers)
    }

    @Test
    // Test read success with multiple line include header
    fun readSuccessWithMultipleLineIncludeHeader() {
        products = Mock().products(10, 1, 100)
        CsvWriter<Product>().write(filePath, products, headers)
        val resProducts: List<Product>? = csvRead.read(filePath)

        // Except list products same with list read list products
        assertEquals(products, resProducts)

        assertEquals(headers, csvRead.headers)
    }

    @Test
    fun readErrorWithMultipleLineIncludeHeader() {
        val products = Mock().products(10, 1, 100)
        CsvWriter<Product>().write(filePath, products, headers)
        FileHelpers().updateFile(filePath, 2, 1, "Test")
        FileHelpers().updateFile(filePath, 1, 8, "Test")
        val exception = kotlin.runCatching {
            csvRead.read<Product>(filePath)
        }

        // Except read success with 8 product
        exception.onSuccess {
            assertNotSame(products, it)
            assertEquals(8, it?.size)
        }
    }

    @Test
    // Test read when change permission in file
    fun readSuccessWithPermission() {
        val products = Mock().products(10, 1, 100)
        CsvWriter<Product>().write(filePath, products, headers)

        // Remove permission execute in file
        FileUtils().removePermission(filePath, PosixFilePermission.GROUP_EXECUTE)
        FileUtils().removePermission(filePath, PosixFilePermission.OWNER_EXECUTE)
        FileUtils().removePermission(filePath, PosixFilePermission.OTHERS_EXECUTE)

        // List products read same list when create
        assertEquals(products, csvRead.read<Product>(filePath))
    }
}
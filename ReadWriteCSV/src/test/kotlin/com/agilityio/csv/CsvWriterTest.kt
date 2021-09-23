package com.agilityio.csv

import com.agilityio.helpers.Mock
import com.agilityio.product.Product
import com.agilityio.utils.FieldHelpers
import com.agilityio.utils.FileUtils
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.*
import java.nio.file.attribute.PosixFilePermission


internal class CsvWriterTest {

    private val headers: List<String> = FieldHelpers().getAllModelFieldsName(Product::class.java)
    private lateinit var products: List<Product>
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

        CsvWriter<Product>().write(filePath, products, null)
        val file = File(filePath)
        val count = BufferedReader(FileReader(file)).lines().count()

        assertTrue(file.exists())
        assertEquals(2, count)
    }

    @Test
    // Test write csv success include header
    fun writeCsvSuccessWithOneLine() {
        products = Mock().products(1, 1, 10)

        CsvWriter<Product>().write(filePath, products, headers)
        val file = File(filePath)
        val count = BufferedReader(FileReader(filePath)).lines().count()

        assertTrue(file.exists())
        assertEquals(2, count)
    }

    @Test
    // Test write csv success include header
    fun writeCsvErrorWithOneLine() {
        products = mutableListOf()
        CsvWriter<Product>()
        val csvWriter =CsvWriter<Product>()
        val exception: Throwable =
            assertThrows(IOException::class.java) { csvWriter.write(filePath, products, null) }
        assertEquals("Data object not exists", exception.message)
    }

    @Test
    // Test write csv success not include header
    fun writeCsvSuccessIgnoreHeaderWithMultiplesLine() {
        products = Mock().products(10, 1, 10)

        CsvWriter<Product>().write(filePath, products, null)
        val file = File(filePath)
        val count = BufferedReader(FileReader(filePath)).lines().count()

        assertTrue(file.exists())
        assertEquals(11, count)
    }

    @Test
    // Test write csv success not include header
    fun writeCsvSuccessWithMultiplesLine() {
        products = Mock().products(10, 1, 10)

        CsvWriter<Product>().write(filePath, products, headers)
        val file = File(filePath)
        val count = BufferedReader(FileReader(file)).lines().count()

        assertTrue(file.exists())
        assertEquals(11, count)
    }

    @Test
    // Test when change permission in file
    fun writeSuccessWithPermission() {
        val products = Mock().products(10, 1, 100)

        // Create file and remove permission execute in file
        FileUtils().create(filePath)
        FileUtils().removePermission(filePath, PosixFilePermission.GROUP_EXECUTE)
        FileUtils().removePermission(filePath, PosixFilePermission.OWNER_EXECUTE)
        FileUtils().removePermission(filePath, PosixFilePermission.OTHERS_EXECUTE)

        // List products read same list when create
        CsvWriter<Product>().write(filePath, products, headers)
    }

    @Test
    // Test when write in big file
    fun writeErrorWithBigFile() {
//        val products = Mock().products(2000000, 1, 2100000)

        // List products read same list when create
//        val throwable = assertThrows(OutOfMemoryError::class.java) { CsvWriter<Product>().write(filePath, products, headers) }
//        assertEquals(FileNotFoundException::class.java, throwable.javaClass)
    }
}
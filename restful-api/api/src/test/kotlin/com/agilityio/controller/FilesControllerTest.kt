package com.agilityio.controller

import com.agilityio.csv.CsvWriter
import com.agilityio.helpers.Mock
import com.agilityio.model.Product
import com.agilityio.repository.ProductRepository
import com.agilityio.utils.FieldUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockServletContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.io.File
import java.nio.file.Files
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension::class)
internal class FilesControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var productRepository: ProductRepository

    @InjectMocks
    lateinit var controller: FilesController

    private lateinit var products: List<Product>
    private val headers: List<String> = FieldUtils().getAllModelFieldsName(Product::class.java)
    private val filePath: String = "test.csv"

    @Before
    fun setUp() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(this.webApplicationContext)
            .build()
    }

    @AfterEach
    fun tearDown() {
        productRepository.deleteAll()
    }

    @Test
    fun givenWac_whenServletContext_thenItProvidesFilesController() {
        val servletContext = webApplicationContext.servletContext
        Assert.assertNotNull(servletContext)
        Assert.assertTrue(servletContext is MockServletContext)
        Assert.assertNotNull(webApplicationContext.getBean("filesController"))
    }

    @Test
    fun uploadSuccessFileCsvWithOneProduct() {
        products = Mock().products(1, 1, 100)
        CsvWriter<Product>().write(filePath, products, null)

        val file = File(filePath)
        val bytes: ByteArray = Files.readAllBytes(file.toPath())
        val csvFile = MockMultipartFile("data", filePath, "text/plain", bytes)

        // when
        mockMvc.perform(
            multipart("/v1.0/api/files")
                .file("file", csvFile.bytes)
                .contextPath("/v1.0/api")
                .characterEncoding("UTF-8"))
            .andExpect(status().isOk)

        assertEquals(products, productRepository.findAll())
    }

    @Test
    fun uploadSuccessFileCsvWithMultipleProduct() {
        products = Mock().products(10, 1, 100)
        CsvWriter<Product>().write(filePath, products, null)

        val file = File(filePath)
        val bytes: ByteArray = Files.readAllBytes(file.toPath())
        val csvFile = MockMultipartFile("data", filePath, "text/plain", bytes)

        // when
        mockMvc.perform(
            multipart("/v1.0/api/files")
                .file("file", csvFile.bytes)
                .contextPath("/v1.0/api")
                .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun uploadBigFileError() {
        products = Mock().products(200000, 1, 2000000)
        CsvWriter<Product>().write(filePath, products, null)

        val file = File(filePath)
        val bytes: ByteArray = Files.readAllBytes(file.toPath())
        val csvFile = MockMultipartFile("data", filePath, "text/plain", bytes)

        // when
        mockMvc.perform(
            multipart("/v1.0/api/files")
                .file("file", csvFile.bytes)
                .contextPath("/v1.0/api")
                .characterEncoding("UTF-8"))
            .andExpect(status().isExpectationFailed)
    }

    @Test
    fun downloadProductCsvFile() {
        // when
        val response: MockHttpServletResponse = mockMvc.perform(
            get("/v1.0/api/files/products.csv")
                .contextPath("/v1.0/api")
        ).andReturn().response

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        val content: String = response.contentAsString
        assertNotNull(content)
    }
}
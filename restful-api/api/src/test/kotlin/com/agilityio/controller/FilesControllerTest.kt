package com.agilityio.controller

import com.agilityio.csv.CsvWriter
import com.agilityio.helpers.Mock
import com.agilityio.model.Product
import com.agilityio.utils.FieldUtils
import com.agilityio.utils.FileUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.io.File
import java.nio.file.Files

@WebMvcTest
internal class FilesControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    private lateinit var products: List<Product>
    private val headers: List<String> = FieldUtils().getAllModelFieldsName(Product::class.java)
    private val filePath: String = "test.csv"
    private val file: File = FileUtils().create("test.csv")

    @Test
    fun uploadSuccessFileCsvWithOneProduct() {
        products = Mock().products(1, 1, 100)
        CsvWriter<Product>().write(filePath, products, null)

        val file = File(filePath)
        val bytes: ByteArray = Files.readAllBytes(file.toPath())

        // when
        val response: MockHttpServletResponse = mockMvc.perform(
            post("/v1.0/api/files/").contentType(MediaType.MULTIPART_FORM_DATA).content(
                bytes
            )
        ).andReturn().getResponse()

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun uploadSuccessFileCsvWithMultipleProduct() {
        products = Mock().products(10, 1, 100)
        CsvWriter<Product>().write(filePath, products, null)

        val file = File(filePath)
        val bytes: ByteArray = Files.readAllBytes(file.toPath())

        // when
        val response: MockHttpServletResponse = mockMvc.perform(
            post("/v1.0/api/files/").contentType(MediaType.MULTIPART_FORM_DATA).content(
                bytes
            )
        ).andReturn().getResponse()

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK)
    }
}
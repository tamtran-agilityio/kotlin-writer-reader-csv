package com.agilityio.controller

import com.agilityio.exception.FileUploadExceptionAdvice
import com.agilityio.model.Product
import com.agilityio.repository.ProductRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

internal class FilesControllerTest: MockitoExtension() {

    private var mvc: MockMvc? = null

    @Mock
    private val productRepository: ProductRepository? = null

    @InjectMocks
    private val filesController: FilesController? = null

    // This object will be magically initialized by the initFields method below.
    private val jsonProduct: JacksonTester<Product>? = null
    @BeforeEach
    fun setUp() {
        JacksonTester.initFields(this, ObjectMapper())

        // MockMvc standalone approach
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(filesController)
            .setControllerAdvice(FileUploadExceptionAdvice())
            .build()
    }

    @AfterEach
    fun tearDown() {
    }
}
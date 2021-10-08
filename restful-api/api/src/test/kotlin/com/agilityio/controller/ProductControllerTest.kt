package com.agilityio.controller

import com.agilityio.repository.ProductRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockServletContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension::class)
internal class ProductControllerTest {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun givenWac_whenServletContext_thenItProvidesProductController() {
        val servletContext = webApplicationContext.servletContext
        Assert.assertNotNull(servletContext)
        Assert.assertTrue(servletContext is MockServletContext)
        Assert.assertNotNull(webApplicationContext.getBean("productController"))
    }

    @Test
    fun getProductShouldReturnListProducts() {
        // when
        val response: MockHttpServletResponse = this.mockMvc.perform(
            get("/v1.0/api/products")
                .contextPath("/v1.0/api")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn().response

        // then
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
    }
}
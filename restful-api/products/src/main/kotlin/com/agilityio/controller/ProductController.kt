package com.agilityio.controller

import com.agilityio.csv.CsvWriter
import com.agilityio.model.Product
import com.agilityio.repository.ProductRepository
import com.agilityio.utils.FileUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(private val productRepository: ProductRepository) {

    @GetMapping
    fun getAllProducts(): List<Product> =
        productRepository.findAll()

    @GetMapping("/test")
    fun write() {
        val products = listOf(
            Product(
                1,
                21.0,
                "Phone",
                "Box",
                "K233234",
                "../image.png",
                "Image",
                1.0,
                210.0,
                "Box",
                1,
                "Box",
                2423,
                "Iphone",
                "Apple mobile",
                "Test",
                "Active",
                2,
                "Test",
                true,
                "Kg",
                0.1,
                0.1,
                "cm",
                "USD",
                "$",
                20.0
            ),
            Product(
                2,
                22.0,
                "Phone",
                "Box",
                "K233234",
                "../image.png",
                "Image",
                1.0,
                210.0,
                "Box",
                1,
                "Box",
                2423,
                "Iphone",
                "Apple mobile",
                "Test",
                "Active",
                2,
                "Test",
                true,
                "Kg",
                0.1,
                0.1,
                "cm",
                "USD",
                "$",
                20.0
            )
        )
        CsvWriter<Product>().write("products.csv", products, null)
    }
}
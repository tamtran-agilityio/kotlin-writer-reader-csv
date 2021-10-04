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
}
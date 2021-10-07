package com.agilityio.controller

import com.agilityio.csv.CsvWriter
import com.agilityio.model.Product
import com.agilityio.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController {

    @Autowired
    lateinit var productRepository: ProductRepository

    @GetMapping
    fun getAllProducts(): List<Product> = productRepository.findAll()
}
package com.agilityio.controller

import com.agilityio.csv.CsvReader
import com.agilityio.model.Product
import com.agilityio.repository.ProductRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
@RequestMapping("/files")
class FilesController(
    private val csvRead: CsvReader,
    private val productRepository: ProductRepository
) {
    @PostMapping
    fun upload(@RequestParam("file") file: File) {
        var products: List<Product>? = csvRead.read<Product>(file)
        productRepository.saveAll(products)
    }

}
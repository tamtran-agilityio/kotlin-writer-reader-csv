package com.agilityio.product

import com.agilityio.csv.CsvWriter
import com.agilityio.utils.FieldUtils

/**
 * Implement write list products
 */
class ProductWriter {
    data class Builder(
        var header: List<String> = listOf(),
        var products: List<Product> = listOf()
    ) {
        fun header(header: List<String>) = apply { this.header = header }
        fun products(products: List<Product>) = apply { this.products = products }
        fun build(filePath: String) = run {
            CsvWriter<Product>().write(filePath, this@Builder.products, this@Builder.header)
        }
    }
}

fun main() {
    val header: List<String> = FieldUtils().getAllModelFieldsName(Product::class.java)
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

    ProductWriter.Builder()
        .header(header)
        .products(products)
        .build("products.csv")
}
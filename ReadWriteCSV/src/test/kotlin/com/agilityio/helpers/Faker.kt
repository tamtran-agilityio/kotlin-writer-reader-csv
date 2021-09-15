package com.agilityio.helpers

import com.agilityio.product.Product

class Faker {
    fun products(number: Int, start: Int, end: Int) {
        val numbers = (start..end).shuffled().take(number).toSet()
        val listProduct = mutableListOf<Product>()

        numbers.stream().forEach {

        }
    }

}
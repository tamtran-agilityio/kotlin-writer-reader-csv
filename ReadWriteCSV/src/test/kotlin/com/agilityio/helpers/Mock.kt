package com.agilityio.helpers

import com.agilityio.product.Product
import com.github.javafaker.Faker

class Mock {
    fun products(number: Int, start: Long, end: Long): MutableList<Product> {
        val faker = Faker()
        val numbers = (start..end).shuffled().take(number).toSet()
        val products = mutableListOf<Product>()

        numbers.forEach {
            products.add(
                Product(
                    it,
                    faker.number().randomDouble(1, 1, number),
                    faker.book().title(),
                    faker.address().countryCode(),
                    faker.address().citySuffix(),
                    faker.book().title(),
                    faker.animal().name(),
                    faker.number().randomDouble(1, 1, number),
                    faker.number().randomDouble(1, 1, number),
                    faker.address().firstName(),
                    faker.number().randomDigit(),
                    faker.address().city(),
                    faker.number().randomNumber(),
                    faker.address().firstName(),
                    faker.address().firstName(),
                    faker.address().lastName(),
                    faker.address().firstName(),
                    faker.number().randomNumber(),
                    faker.address().secondaryAddress(),
                    faker.bool().bool(),
                    faker.address().streetAddressNumber(),
                    faker.number().randomDouble(1, 1, number),
                    faker.number().randomDouble(1, 1, number),
                    faker.address().stateAbbr(),
                    faker.business().creditCardType(),
                    faker.business().creditCardNumber(),
                    faker.number().randomDouble(1, 1, number)
                )
            )
        }
        return products
    }

}
package com.agilityio.helpers

import com.agilityio.model.Product
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
                    faker.number().randomDouble(2, 10, 100),
                    faker.book().author(),
                    faker.address().countryCode(),
                    faker.address().citySuffix(),
                    faker.book().author(),
                    faker.animal().name(),
                    faker.number().randomDouble(2, 1, 100),
                    faker.number().randomDouble(2, 1, 100),
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
                    faker.number().randomDouble(2, 1, 100),
                    faker.number().randomDouble(2, 1, 100),
                    faker.address().stateAbbr(),
                    faker.business().creditCardType(),
                    faker.business().creditCardNumber(),
                    faker.number().randomDouble(2, 1, 100)
                )
            )
        }
        return products
    }
}
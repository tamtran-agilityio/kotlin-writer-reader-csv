package com.agilityio.repository

import com.agilityio.model.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: MongoRepository<Product, Long> {
}
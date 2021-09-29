package com.agilityio.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@ConfigurationProperties("mongo")
@Configuration
class MongoConfig {
    lateinit var url: String

    @Bean
    fun mongo(): MongoClient {
        val connectionString = ConnectionString(url)
        val mongoClientSettings: MongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        return MongoClients.create(mongoClientSettings)
    }

    @Bean
    fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(mongo(), "products")
    }
}
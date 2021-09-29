package com.agilityio.model

import kotlin.reflect.full.primaryConstructor

/**
 * Data class product
 */
data class Product(
    var id: Long,
    val price: Double,
    val productName: String,
    val uom: String,
    val sku: String,
    val imageUrl: String,
    val imageName: String,
    val packAvgNumberOfUnits: Double,
    val packPricePerUnit: Double,
    val packUnit: String,
    val packSize: Int,
    val packItemUnits: String,
    val vendorId: Long,
    val vendorName: String,
    val description: String,
    val productNote: String,
    val enabled: String,
    val sequenceNumber: Long,
    val productTagsCommaSeparated: String,
    val isPriceByWeight: Boolean,
    val weightUnit: String,
    val pricePerWeightUnit: Double,
    val typicalWeight: Double,
    val typicalWeightUnit: String,
    val currency: String,
    val currencyCode: String,
    val originalPrice: Double
)

package com.agilityio.validator

data class ValidationError(
    val path: String,
    val message: String
)

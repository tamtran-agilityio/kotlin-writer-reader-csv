package com.agilityio.validator

/**
 * Data object to represent the data of an Exception. Convenient for serialization.
 */
data class ValidationErrors<T>(
    val errors : List<ValidationError>
)

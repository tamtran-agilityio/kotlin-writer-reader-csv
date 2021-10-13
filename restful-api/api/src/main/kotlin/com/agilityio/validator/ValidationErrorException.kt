package com.agilityio.validator

import java.io.IOException

class ValidationErrorException(
    private val errors: List<ValidationError>
) : IOException() {

    /***
     * Convenience method for getting a data object from the Exception.
     */
    fun toValidationErrors() = ValidationErrors<Any>(errors)

    /***
     * Convenience method for throw a message object from the Exception.
     */
    fun throwErrorsMessage() {
        errors.forEach {
            throw IOException(it.message)
        }
    }
}
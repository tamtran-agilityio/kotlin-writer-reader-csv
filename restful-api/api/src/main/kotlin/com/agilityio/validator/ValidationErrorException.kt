package com.agilityio.validator

class ValidationErrorException(
    private val errors: List<ValidationError>
) : Exception() {

    /***
     * Convenience method for getting a data object from the Exception.
     */
    fun toValidationErrors() = ValidationErrors<Any>(errors)

    /***
     * Convenience method for throw a message object from the Exception.
     */
    fun throwErrorsMessage() {
        errors.forEach {
            throw Exception(it.message)
        }
    }
}
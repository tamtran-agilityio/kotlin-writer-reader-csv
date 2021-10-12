package com.agilityio.validator

interface Validated {
    /**
     * @throws [ValidationErrorException]
     */
    fun validate(target: Any)
}
package com.agilityio.validator

import org.springframework.stereotype.Component
import org.springframework.validation.*
import org.springframework.web.multipart.MultipartFile

@Component
class FileValidator: Validator {
    override fun supports(clazz: Class<*>) : Boolean {
        return MultipartFile::class.java == clazz
    }

    override
    fun validate(target: Any, errors: Errors) {
        val fileUpload = target as MultipartFile

        if (fileUpload.size <= 0) {
            errors.rejectValue("file", "Missing content file")
        }

        if (fileUpload.originalFilename?.contains(".csv") == false) {
            errors.rejectValue(fileUpload.name, "File format and extension of don’t match")
        }
    }
}
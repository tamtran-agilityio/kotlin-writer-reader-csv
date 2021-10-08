package com.agilityio.validator

import org.apache.tomcat.util.http.fileupload.FileUpload
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import org.springframework.web.multipart.MultipartFile

@Component
class FileValidator: Validator {
    override fun supports(clazz: Class<*>) : Boolean {
        return FileUpload::class.java.isAssignableFrom(clazz)
    }

    override
    fun validate(target: Any, errors: Errors) {
        val fileUpload = target as MultipartFile

        if (fileUpload.size <= 0) {
            errors.rejectValue("files", "Missing content file")
        }

        if (fileUpload.originalFilename?.contains(".csv") == false) {
            errors.rejectValue("files", "‘File format and extension of don’t match")
        }

    }
}
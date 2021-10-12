package com.agilityio.validator

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class FileUploadValidator: Validated {
    override fun validate(target: Any) {
        var validationErrors: MutableList<ValidationError> = mutableListOf<ValidationError>()

        val fileUpload = target as MultipartFile

        if (fileUpload.size <= 0) {
            validationErrors.add(ValidationError("file", "File content not exists"))
        }

        if (fileUpload.originalFilename?.contains(".csv") == false) {
            validationErrors.add(ValidationError("name", "File format and extension of don’t match"))
        }

        ValidationErrorException(validationErrors).throwErrorsMessage()
    }
}
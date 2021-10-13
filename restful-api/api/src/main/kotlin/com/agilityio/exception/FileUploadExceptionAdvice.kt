package com.agilityio.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.IOException

@ControllerAdvice
class FileUploadExceptionAdvice: ResponseEntityExceptionHandler() {
    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxSizeException(exc: MaxUploadSizeExceededException?): ResponseEntity<*>? {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body<Any>(mapOf("message" to "File too large!"))
    }

    @ExceptionHandler(IOException::class)
    fun handleIOException(ex: IOException): ResponseEntity<*>? {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body<Any>(mapOf("message" to ex.message))
    }
}
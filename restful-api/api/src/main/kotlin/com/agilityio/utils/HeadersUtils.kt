package com.agilityio.utils

import org.springframework.http.HttpHeaders
import java.io.File

class HeadersUtils {
    fun attachFile(file: File): HttpHeaders {
        val header = HttpHeaders()
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.name + "\"")
        header.add("Content-Type", "application/csv")
        header.add("Cache-Control", "no-cache, no-store, must-revalidate")
        header.add("Pragma", "no-cache")
        header.add("Expires", "0")
        return header
    }
}
package com.agilityio.csv

import com.agilityio.file.FileInterface
import java.io.File

class CsvFile: FileInterface {
    override fun get(pathFile: String, fileName: String): File {
        return File("$pathFile/$fileName")
    }

    override fun create(fileName: String): File {
        return File(fileName)
    }
}
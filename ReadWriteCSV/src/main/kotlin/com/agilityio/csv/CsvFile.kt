package com.agilityio.csv

import com.agilityio.file.FileInterface
import java.io.File

/**
 * Implement csv file
 */
class CsvFile: FileInterface {
    /**
     * Implement get file
     * @param pathFile path redirect folder container file
     * @param fileName name of file
     * @return file
     */
    override fun get(pathFile: String, fileName: String): File {
        return File("$pathFile/$fileName")
    }

    /**
     * Implement create file
     * @param fileName name of file
     * @return file after create
     */
    override fun create(fileName: String): File {
        return File(fileName)
    }
}
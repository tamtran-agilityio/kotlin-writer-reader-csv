package com.agilityio.csv

import java.io.File

/**
 * Implement csv file
 */
class CsvFile {
    /**
     * Implement get file
     * @param pathFile path redirect folder container file
     * @param fileName name of file
     * @return file
     */
     fun get(pathFile: String, fileName: String): File {
        return File("$pathFile/$fileName")
    }

    /**
     * Implement create file
     * @param fileName name of file
     * @return file after create
     */
     fun create(fileName: String): File {
        return File(fileName)
    }
}
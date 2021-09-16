package com.agilityio.utils

import java.io.File

class FileUtils {
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
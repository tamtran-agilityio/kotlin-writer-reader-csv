package com.agilityio.file

import java.io.File

interface FileInterface {
  fun get(pathFile: String, fileName: String): File
  fun create(fileName: String): File
}
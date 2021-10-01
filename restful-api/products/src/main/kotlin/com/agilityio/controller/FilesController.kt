package com.agilityio.controller

import com.agilityio.csv.CsvReader
import com.agilityio.csv.CsvWriter
import com.agilityio.model.Product
import com.agilityio.repository.ProductRepository
import com.agilityio.utils.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.annotation.Resource


@RestController
@RequestMapping("/files")
class FilesController(
    private val csvRead: CsvReader,
    private val csvWriter: CsvWriter<Product>,
    private val productRepository: ProductRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(FilesController::class.java)

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun upload(@RequestParam("file") file: MultipartFile) {
        try {
            val convFile: File = FileUtils().create(file.originalFilename)
            FileUtils().writeByteArrayToFile(convFile, file.bytes)

            val products: List<Product>? = csvRead.read(convFile)
            if (products?.isEmpty() != true) {
                productRepository.saveAll(products)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @GetMapping("{fileName}")
    @ResponseStatus(HttpStatus.OK)
    fun download(@PathVariable fileName: String): ResponseEntity<ByteArrayResource>? {
        try {
            val products = productRepository.findAll()
            val file: File = FileUtils().create(fileName)
            csvWriter.write(file ,products, null )

            val header = HttpHeaders()
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.name}.csv")
            header.add("Cache-Control", "no-cache, no-store, must-revalidate")
            header.add("Pragma", "no-cache")
            header.add("Expires", "0")

            val path: Path = Paths.get(file.absolutePath)
            val resource = ByteArrayResource(Files.readAllBytes(path))

            return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body<ByteArrayResource>(resource)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}
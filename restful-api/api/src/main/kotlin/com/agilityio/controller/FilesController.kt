package com.agilityio.controller

import com.agilityio.csv.CsvReader
import com.agilityio.csv.CsvWriter
import com.agilityio.helpers.MessageResponse
import com.agilityio.model.Product
import com.agilityio.repository.ProductRepository
import com.agilityio.utils.FieldUtils
import com.agilityio.utils.FileUtils
import com.agilityio.utils.HeadersUtils
import com.agilityio.validator.FileUploadValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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


@RestController
@RequestMapping("/files")
class FilesController {
    private val logger: Logger = LoggerFactory.getLogger(FilesController::class.java)

    @Autowired
    lateinit var csvRead: CsvReader

    @Autowired
    lateinit var csvWriter: CsvWriter<Product>

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var fileValidator: FileUploadValidator

    /**
     * Implement upload file
     * @param file
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun upload(@RequestParam("file") file: MultipartFile): ResponseEntity<*> {
        fileValidator.validate(file)
        val fileName: String? = file.originalFilename
        if (fileName != null) {
            val convFile: File = FileUtils().create("product_${fileName}")
            FileUtils().writeByteArrayToFile(convFile, file.bytes)

            val products: List<Product>? = csvRead.read(convFile)
            if (products?.isEmpty() != true) {
                productRepository.saveAll(products as MutableList<Product>)
            }
        }
        return ResponseEntity
            .ok()
            .body(MessageResponse("Uploaded the file successfully"))
    }

    /**
     * Implement download Csv file
     * @param fileName string file name with extension
     * @return file with all products
     * Example get all products http://localhost:9000/v1.0/api/files/test.csv
     */
    @GetMapping("{fileName}")
    @ResponseStatus(HttpStatus.OK)
    fun download(@PathVariable fileName: String): ResponseEntity<Any> {
        val products = productRepository.findAll()
        if (products.isEmpty()) throw Exception("Products data empty")

        val headers = FieldUtils().getAllModelFieldsName(Product::class.java)
        val file: File = FileUtils().create(fileName)
        csvWriter.write(fileName, products, headers)

        val header: HttpHeaders = HeadersUtils().attachFile(file)
        val path: Path = Paths.get(file.absolutePath)
        val resource = ByteArrayResource(Files.readAllBytes(path))

        return ResponseEntity.ok()
            .headers(header)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource)
    }
}
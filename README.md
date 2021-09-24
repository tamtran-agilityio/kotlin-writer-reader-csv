# PRACTICE

## TECHSTACKS

* Version: Kotlin 1.3.x

## PRACTICE SCOPE

* Write a CSV writer/reader for a list of   products
  * Product data structure:
    * Long id
    * Double price
    * String productName
    * String uom
    * String sku
    * String imageUrl
    * String imageName
    * Double packAvgNumberOfUnits
    * Double packPricePerUnit
    * String packUnit
    * Integer packSize
    * String packItemUnits
    * Long vendorId
    * String vendorName
    * String description
    * String productNote
    * String enabled
    * Long sequenceNumber
    * String productTagsCommaSeparated
    * Boolean isPriceByWeight
    * String weightUnit
    * Double pricePerWeightUnit
    * Double typicalWeight
    * String typicalWeightUnit
    * String currency
    * String currencyCode
    * Double originalPrice
  * Given a list products, write them to a CSV file
  * Given a CSV file with a format of product data item, read them to a list of products
  * Support read/write headers

## Prerequisites
 
  * Kotlin version 1.3.7
  * Java version 11
  * Maven version 3.8.1

## Install and test

  * mvn install
  * mvn test 

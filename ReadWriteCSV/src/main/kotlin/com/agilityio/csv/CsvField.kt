package com.agilityio.csv

import kotlin.reflect.KClass

/**
 * Csv field data object in column of row in Csv file
 */
data class CsvField(val name: String, val type: KClass<out Any>)
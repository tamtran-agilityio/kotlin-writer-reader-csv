package com.agilityio.csv

import kotlin.reflect.KClass

data class CsvField(val name: String, val type: KClass<out Any>)
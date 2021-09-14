package com.agilityio.utils

import kotlin.streams.toList

class FieldHelpers {
    /**
     * Handle get all properties name on class
     */
    fun getAllModelFieldsName(aClass: Class<*>?): List<String> {
        val list = mutableListOf<String>()
        val fields = aClass!!.declaredFields
        val t = fields.iterator()
        while (t.hasNext()) {
            list.add(t.next().name)
        }
        return formatFieldName(list)
    }

    /**
     * Handle format field name
     * @param list list string
     * @return list string name
     */
    private fun formatFieldName(list: List<String>): List<String> {
        return list
            .stream()
            .map { it.replace("(?=[A-Z])".toRegex(), " ").capitalize() }
            .toList()
    }

    /**
     * Implement convert string to type of class
     * @param value string need convert to type
     * @return value with type corresponding
     */
    // the type parameter with the reified modifier to make it accessible inside the function
    @Throws(IllegalStateException::class)
    inline fun <reified T> convertStringTo(value: String?): T {
        return when (T::class) {
            Int::class -> value?.toInt() as T
            String::class -> value as T
            Double::class -> value?.toDouble() as T
            Boolean::class -> value?.toBoolean() as T
            Long::class-> value?.toLong() as T
            // add other types here if you need
            else -> throw IllegalStateException("Unknown Generic Type")
        }
    }
}
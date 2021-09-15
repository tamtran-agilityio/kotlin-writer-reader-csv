package com.agilityio.utils

class LineUtils<T> {
    private val delimitersNewLine: String = "\n"

    fun read(line: String): List<String> {
        return line.split(", ")
    }

    fun write(line: T): StringBuilder {
        val formatObject = FormatObject<T>()
        val valueString = formatObject.toString(line)

        return StringBuilder().append(valueString).append(delimitersNewLine)
    }
}
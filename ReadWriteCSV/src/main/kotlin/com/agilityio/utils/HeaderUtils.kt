package com.agilityio.utils

class HeaderUtils {
    private val delimitersNewLine: String = "\n"

    fun read(line: String): List<String> {
        return line.split(", ")
    }

    fun write(header: List<String>): StringBuilder {
        return StringBuilder().append(header.joinToString()).append(delimitersNewLine)
    }
}
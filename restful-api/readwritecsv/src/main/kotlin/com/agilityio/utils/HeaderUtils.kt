package com.agilityio.utils

/**
 * Implement read/writer header for a Csv file
 */
class HeaderUtils {
    private val delimitersNewLine: String = "\n"

    /**
     * Implement read line header of Csv file
     * @param line String text when read in Csv file
     * @return list field in Csv file
     */
    fun read(line: String): List<String> {
        try {
            return line.split(", ")
        } catch (ex: Exception) {
            throw Exception("Not exist headers")
        }
    }

    /**
     * Implement builder string from list item
     * @param header list need convert to string
     * @return String builder of line
     */
    fun write(header: List<String>): StringBuilder {
        return StringBuilder().append(header.joinToString()).append(delimitersNewLine)
    }
}
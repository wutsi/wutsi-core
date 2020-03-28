package com.wutsi.core.logging

import java.util.regex.Pattern


class LoggerEncoder {

    fun encode(value: String?): String? {
        if (value == null) {
            return null
        }

        /* replace CR by ' ' */
        val xvalue = value.replace('\n', ' ')

        /* Enclose string with space in " */
        return if (DQUOTE_PATTERN.matcher(xvalue).find()) String.format("\"%s\"", xvalue) else xvalue
    }

    companion object {
        private val DQUOTE_PATTERN = Pattern.compile("\\s|,|=")
    }
}

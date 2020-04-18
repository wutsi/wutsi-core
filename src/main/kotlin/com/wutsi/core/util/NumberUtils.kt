package com.wutsi.core.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.StringCharacterIterator

object NumberUtils {
    fun toHumanReadable(
            value: Long,
            fmt: NumberFormat = DecimalFormat("#.#")
    ): String {
        var bytes = value
        if (bytes == 0L) {
            return ""
        } else if (-1000 < bytes && bytes < 1000) {
            return bytes.toString()
        }
        val ci = StringCharacterIterator("KMGTPE")
        while (bytes <= -999950 || bytes >= 999950) {
            bytes /= 1000
            ci.next()
        }

        return fmt.format(bytes / 1000.0) + " " + ci.current()
    }
}

package com.wutsi.core.util

import org.junit.Assert.assertEquals
import org.junit.Test

class NumberUtilsTest {
    @Test
    fun toHumanReadable() {
        validate("", 0L)
        validate("27", 27L)
        validate("999", 999L)
        validate("1.4 K", 1440L)
        validate("2 K", 1990L)
        validate("110.6 K", 110592L)
        validate("7.1 M", 7077888)
        validate("453 M", 452984832)
        validate("29 G", 28991029248)
        validate("1.9 T", 1855425871872)
    }

    private fun validate(expected: String, number: Long) {
        assertEquals(expected, NumberUtils.toHumanReadable(number))
    }
}

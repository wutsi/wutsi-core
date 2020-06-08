package com.wutsi.core.util

import org.junit.Assert.assertEquals
import org.junit.Test

class DurationUtilsTest {
    @Test
    fun toHumanReadable() {
        validate("", 0L)
        validate("27s", 27L)
        validate("5m 2s", 302L)
        validate("1h", 3600L)
        validate("1h 5m", 3902L)
    }

    private fun validate(expected: String, number: Long) {
        assertEquals(expected, DurationUtils.secondsToHumanReadable(number))
    }
}

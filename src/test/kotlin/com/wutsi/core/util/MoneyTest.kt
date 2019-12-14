package com.wutsi.core.util

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class MoneyTest {
    @Test
    fun testToString() {
        assertEquals("100,000 XAF", Money(BigDecimal(100000), "XAF").toString())
        assertEquals("100,000.40 NZD", Money(BigDecimal(100000.40), "NZD").toString())
        assertEquals("100,000.99 US$", Money(BigDecimal(100000.99), "USD").toString())
        assertEquals("100,000.00 EUR", Money(BigDecimal(100000.00), "EUR").toString())
        assertEquals("100,000.40 GBP", Money(BigDecimal(100000.40), "GBP").toString())
    }

    @Test
    fun testToDouble() {
        assertEquals(100000.00, Money(BigDecimal(100000.00), "XAF").toDouble())
        assertEquals(100000.65, Money(BigDecimal(100000.65), "USD").toDouble())
        assertEquals(100000.40, Money(BigDecimal(100000.40), "CAD").toDouble())
    }
}

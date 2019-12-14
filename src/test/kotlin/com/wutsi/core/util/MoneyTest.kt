package com.wutsi.core.util

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Test
import java.math.BigDecimal

class MoneyTest {
    @Test
    fun testToString() {
        assertEquals("", Money(null, "XAF").toString())
        assertEquals("", Money(BigDecimal(100000), null).toString())
        assertEquals("", Money(BigDecimal(100000), "").toString())
        assertEquals("", Money(BigDecimal(100000), "???").toString())

        assertEquals("100,000 XAF", Money(BigDecimal(100000), "XAF").toString())
        assertEquals("100,000.40 NZD", Money(BigDecimal(100000.40), "NZD").toString())
        assertEquals("100,000.99 USD", Money(BigDecimal(100000.99), "USD").toString())
        assertEquals("100,000.00 EUR", Money(BigDecimal(100000.00), "EUR").toString())
        assertEquals("100,000.40 GBP", Money(BigDecimal(100000.40), "GBP").toString())
    }

    @Test
    fun testToDouble() {
        assertEquals(100000.00, Money(BigDecimal(100000.00), "XAF").toDouble())
        assertEquals(100000.65, Money(BigDecimal(100000.65), "USD").toDouble())
        assertEquals(100000.40, Money(BigDecimal(100000.40), "CAD").toDouble())
    }

    @Test
    fun testMultiply() {
        val result = Money(150.99.toBigDecimal(), "USD").multiply(2.toBigDecimal())

        assertEquals(301.98.toBigDecimal(), result.value)
        assertEquals("USD", result.currencyCode)
    }

    @Test
    fun testMultiplyNull() {
        val result = Money(null, "USD").multiply(2.toBigDecimal())

        assertNull(result.value)
        assertEquals("USD", result.currencyCode)
    }
}

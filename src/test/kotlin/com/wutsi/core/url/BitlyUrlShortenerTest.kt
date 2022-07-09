package com.wutsi.core.url

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BitlyUrlShortenerTest {
    val service = BitlyUrlShortener("7c6a88dd1ca7633b0d5e15336184848e0ec5d22c")

    @Test
    fun shorten() {
        var short = service.shorten("https://www.google.ca")
        assertEquals("https://bit.ly/2KPbcAE", short)
    }
}

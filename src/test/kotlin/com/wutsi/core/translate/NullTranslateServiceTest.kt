package com.wutsi.core.translate

import org.junit.Test

import org.junit.Assert.*

class NullTranslateServiceTest {
    val tranlate = NullTranslateService()

    @Test
    fun translate() {
        assertEquals("foo", tranlate.translate(null, "en", "foo"))
    }
}

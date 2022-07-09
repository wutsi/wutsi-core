package com.wutsi.core.translate

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NullTranslateServiceTest {
    val tranlate = NullTranslateService()

    @Test
    fun translate() {
        assertEquals("foo", tranlate.translate(null, "en", "foo"))
    }
}

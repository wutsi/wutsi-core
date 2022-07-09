package com.wutsi.core.logging


import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import java.io.IOException
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class KVLoggerImplTest {
    @Mock
    private lateinit var logger: Logger

    private lateinit var kv: KVLoggerImpl

    @BeforeEach
    fun setUp() {
        kv = KVLoggerImpl(logger, LoggerEncoder())
    }

    @Test
    fun shouldLog() {
        // Given
        kv.add("foo", "bar")
        kv.add("john", "doe")
        kv.add("valueLong", 1L)
        kv.add("valueInt", 2)
        kv.add("valueDouble", 3.5)

        // When
        kv.log()

        // Then
        verify(logger).info("foo=bar john=doe valueDouble=3.5 valueInt=2 valueLong=1")
    }

    @Test
    fun shouldNotLogWhenEmpty() {
        // When
        kv.log()

        // Then
        verify(logger, never()).info(anyString())
    }

    @Test
    fun shouldLogMultiValue() {
        // Given
        kv.add("foo", "john")
        kv.add("foo", "doe")

        // When
        kv.log()

        // Then
        verify(logger).info("foo=\"john doe\"")
    }

    @Test
    fun shouldLogWithSortedKeys() {
        // Given
        kv.add("Z", "bar")
        kv.add("A", "doe")

        // When
        kv.log()

        // Then
        verify(logger).info("A=doe Z=bar")
    }

    @Test
    fun shouldLogException() {
        // Given
        kv.add("A", "bar")
        kv.add("Z", "doe")

        val ex = IOException("error")

        // When
        kv.log(ex)

        // Then
        val msg = ArgumentCaptor.forClass(String::class.java)
        val exception = ArgumentCaptor.forClass(Throwable::class.java)
        verify(logger).error(msg.capture(), exception.capture())
        assertEquals("A=bar Exception=java.io.IOException ExceptionMessage=error Z=doe", msg.value)
        assertEquals(ex, exception.value)
    }

    @Test
    @Throws(Exception::class)
    fun shouldLogAMaximumOf10000Characters() {
        // Given
        val ch100 =
            "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
        val longString = StringBuilder()
        for (i in 0..999) {
            longString.append(ch100).append('\n')
        }

        kv.add("foo", "bar")
        kv.add("john", "smith")
        kv.add("name", longString.toString())

        // When
        kv.log()

        // Then
        val msg = ArgumentCaptor.forClass(String::class.java)
        verify(logger).info(msg.capture())
        assertEquals(KVLoggerImpl.MAX_LENGTH, msg.value.length)
    }

    @Test
    fun shouldNotLogNullValue() {
        // Given
        kv.add("foo", "bar")
        kv.add("john", null as String?)

        // When
        kv.log()

        // Then
        verify(logger).info("foo=bar")
    }
}

package com.wutsi.core.logging

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.core.http.TraceContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.io.IOException
import java.time.Clock
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.test.fail

@ExtendWith(MockitoExtension::class)
class KVLoggerFilterTest {
    @Mock
    private lateinit var kv: KVLogger

    @Mock
    private lateinit var request: HttpServletRequest

    @Mock
    private lateinit var response: HttpServletResponse

    @Mock
    private lateinit var chain: FilterChain

    @Mock
    private lateinit var clock: Clock

    private lateinit var filter: KVLoggerFilter

    @BeforeEach
    fun setUp() {
        filter = KVLoggerFilter(kv, clock)

        doReturn("/foo/bar").whenever(request).requestURI
        doReturn("gzip").whenever(request).getHeader("Accept-Encoding")
        doReturn("text/plain").whenever(request).getHeader("Content-Type")
        doReturn("bearer: 320930293029").whenever(request).getHeader("Authorization")
        doReturn("client-id").whenever(request).getHeader(TraceContext.CLIENT_ID)
        doReturn("trace-id").whenever(request).getHeader(TraceContext.TRACE_ID)
        doReturn("device-uid").whenever(request).getHeader(TraceContext.DEVICE_UID)
        doReturn("message-id").whenever(request).getHeader(TraceContext.MESSAGE_ID)
        doReturn("parent-message-id").whenever(request).getHeader(TraceContext.PARENT_MESSAGE_ID)

        doReturn("text/xml").whenever(response).getHeader("Content-Type")
        doReturn("gzip").whenever(response).getHeader("Content-Encoding")
        doReturn("555").whenever(response).getHeader("Content-Length")
    }

    @Test
    @Throws(Exception::class)
    fun shouldLog() {
        // Given
        doReturn(201).whenever(response).status

        `when`(clock.millis())
            .thenReturn(1000L)
            .thenReturn(1100L)

        val value1 = arrayOf("value1.1")
        val value2 = arrayOf("value2.1", "value2.2")
        `when`(request.parameterMap).thenReturn(
            mapOf(
                "param1" to value1,
                "param2" to value2
            )
        )


        // When
        filter.doFilter(request, response, chain)

        // Then
        verify(kv).add("HttpRequestAuthorization", "bearer: 320930293029")
        verify(kv).add("HttpRequestURI", "/foo/bar")
        verify(kv).add("HttpRequestEncoding", "gzip")
        verify(kv).add("HttpRequestType", "text/plain")
        verify(kv).add("HttpResponseType", "text/xml")
        verify(kv).add("HttpResponseEncoding", "gzip")
        verify(kv).add("HttpResponseLength", "555")
        verify(kv).add("HttpResponseStatus", 201L)
        verify(kv).add("Latency", 100L)
        verify(kv).add("Success", true)

        verify(kv).add(TraceContext.CLIENT_ID, "client-id")
        verify(kv).add(TraceContext.PARENT_MESSAGE_ID, "parent-message-id")
        verify(kv).add(TraceContext.MESSAGE_ID, "message-id")
        verify(kv).add(TraceContext.DEVICE_UID, "device-uid")
        verify(kv).add(TraceContext.TRACE_ID, "trace-id")

        verify(kv).add("param1", value1.toList())
        verify(kv).add("param2", value2.toList())

        verify(kv).log()
    }

    @Test
    @Throws(Exception::class)
    fun shouldLogIOException() {
        // Given
        val ex = IOException("Error")
        doThrow(ex).`when`<FilterChain>(chain).doFilter(ArgumentMatchers.any(), ArgumentMatchers.any())

        doReturn(1000L).doReturn(1110L).whenever(clock).millis()

        try {
            filter.doFilter(request, response, chain)

            fail()
        } catch (e: IOException) {

            // Then
            verify(kv).add("HttpRequestAuthorization", "bearer: 320930293029")
            verify(kv).add("HttpRequestURI", "/foo/bar")
            verify(kv).add("HttpRequestEncoding", "gzip")
            verify(kv).add("HttpRequestType", "text/plain")
            verify(kv).add("HttpResponseType", "text/xml")
            verify(kv).add("HttpResponseEncoding", "gzip")
            verify(kv).add("HttpResponseLength", "555")
            verify(kv).add("Latency", 110L)
            verify(kv).add("Success", false)
            verify(kv).add("HttpResponseStatus", 500L)

            verify(kv).add(TraceContext.CLIENT_ID, "client-id")
            verify(kv).add(TraceContext.PARENT_MESSAGE_ID, "parent-message-id")
            verify(kv).add(TraceContext.MESSAGE_ID, "message-id")
            verify(kv).add(TraceContext.DEVICE_UID, "device-uid")
            verify(kv).add(TraceContext.TRACE_ID, "trace-id")

            verify(kv).log(e)
        }

    }

    @Test
    @Throws(Exception::class)
    fun shouldLogServletException() {
        // Given
        doReturn(500).whenever(response).status

        val ex = ServletException("Error")
        doThrow(ex).`when`<FilterChain>(chain).doFilter(ArgumentMatchers.any(), ArgumentMatchers.any())

        try {
            // When
            filter.doFilter(request, response, chain)

            // Then
            fail()

        } catch (e: ServletException) {

            // Then
            verify(kv).log(e)
        }

    }

    @Test
    @Throws(Exception::class)
    fun shouldLogRuntimeException() {
        // Given
        doReturn(500).whenever(response).status

        val ex = IllegalStateException("Error")
        doThrow(ex).whenever(chain).doFilter(ArgumentMatchers.any(), ArgumentMatchers.any())

        try {
            filter.doFilter(request, response, chain)

            fail()

        } catch (e: RuntimeException) {
            verify(kv).log(e)
        }

    }
}

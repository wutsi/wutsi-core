package com.wutsi.core.logging

import com.wutsi.core.http.TraceContext
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.time.Clock
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RunWith(MockitoJUnitRunner::class)
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

    @Before
    fun setUp() {
        filter = KVLoggerFilter(kv, clock)

        `when`(request.requestURI).thenReturn("/foo/bar")
        `when`(request.getHeader("Accept-Encoding")).thenReturn("gzip")
        `when`(request.getHeader("Content-Type")).thenReturn("text/plain")
        `when`(request.getHeader("Authorization")).thenReturn("bearer: 320930293029")
        `when`(request.getHeader(TraceContext.CLIENT_ID)).thenReturn("client-id")
        `when`(request.getHeader(TraceContext.TRACE_ID)).thenReturn("trace-id")
        `when`(request.getHeader(TraceContext.DEVICE_UID)).thenReturn("device-uid")
        `when`(request.getHeader(TraceContext.MESSAGE_ID)).thenReturn("message-id")
        `when`(request.getHeader(TraceContext.PARENT_MESSAGE_ID)).thenReturn("parent-message-id")

        `when`(response.getHeader("Content-Type")).thenReturn("text/xml")
        `when`(response.getHeader("Content-Encoding")).thenReturn("gzip")
        `when`(response.getHeader("Content-Length")).thenReturn("555")
    }

    @Test
    @Throws(Exception::class)
    fun shouldLog() {
        // Given
        `when`(response.status).thenReturn(201)

        `when`(clock.millis())
                .thenReturn(1000L)
                .thenReturn(1100L)

        val value1 = arrayOf("value1.1")
        val value2 = arrayOf("value2.1", "value2.2")
        `when`(request.parameterMap).thenReturn(mapOf(
                "param1" to value1,
                "param2" to value2
        ))


        // When
        filter.doFilter(request, response, chain)

        // Then
        verify<KVLogger>(kv).add("HttpRequestAuthorization", "bearer: 320930293029")
        verify<KVLogger>(kv).add("HttpRequestURI", "/foo/bar")
        verify<KVLogger>(kv).add("HttpRequestEncoding", "gzip")
        verify<KVLogger>(kv).add("HttpRequestType", "text/plain")
        verify<KVLogger>(kv).add("HttpResponseType", "text/xml")
        verify<KVLogger>(kv).add("HttpResponseEncoding", "gzip")
        verify<KVLogger>(kv).add("HttpResponseLength", "555")
        verify<KVLogger>(kv).add("HttpResponseStatus", 201L)
        verify<KVLogger>(kv).add("Latency", 100L)
        verify<KVLogger>(kv).add("Success", true)

        verify<KVLogger>(kv).add(TraceContext.CLIENT_ID, "client-id")
        verify<KVLogger>(kv).add(TraceContext.PARENT_MESSAGE_ID, "parent-message-id")
        verify<KVLogger>(kv).add(TraceContext.MESSAGE_ID, "message-id")
        verify<KVLogger>(kv).add(TraceContext.DEVICE_UID, "device-uid")
        verify<KVLogger>(kv).add(TraceContext.TRACE_ID, "trace-id")

        verify<KVLogger>(kv).add("param1", value1.toList())
        verify<KVLogger>(kv).add("param2", value2.toList())

        verify<KVLogger>(kv).log()
    }

    @Test
    @Throws(Exception::class)
    fun shouldLogIOException() {
        // Given
        val ex = IOException("Error")
        doThrow(ex).`when`<FilterChain>(chain).doFilter(ArgumentMatchers.any(), ArgumentMatchers.any())

        `when`(clock.millis())
                .thenReturn(1000L)
                .thenReturn(1110L)

        try {
            // When
            filter.doFilter(request, response, chain)

            // Then
            fail()

        } catch (e: IOException) {

            // Then
            verify<KVLogger>(kv).add("HttpRequestAuthorization", "bearer: 320930293029")
            verify<KVLogger>(kv).add("HttpRequestURI", "/foo/bar")
            verify<KVLogger>(kv).add("HttpRequestEncoding", "gzip")
            verify<KVLogger>(kv).add("HttpRequestType", "text/plain")
            verify<KVLogger>(kv).add("HttpResponseType", "text/xml")
            verify<KVLogger>(kv).add("HttpResponseEncoding", "gzip")
            verify<KVLogger>(kv).add("HttpResponseLength", "555")
            verify<KVLogger>(kv).add("Latency", 110L)
            verify<KVLogger>(kv).add("Success", false)
            verify<KVLogger>(kv).add("HttpResponseStatus", 500L)

            verify<KVLogger>(kv).add(TraceContext.CLIENT_ID, "client-id")
            verify<KVLogger>(kv).add(TraceContext.PARENT_MESSAGE_ID, "parent-message-id")
            verify<KVLogger>(kv).add(TraceContext.MESSAGE_ID, "message-id")
            verify<KVLogger>(kv).add(TraceContext.DEVICE_UID, "device-uid")
            verify<KVLogger>(kv).add(TraceContext.TRACE_ID, "trace-id")

            verify<KVLogger>(kv).log(e)
        }

    }

    @Test
    @Throws(Exception::class)
    fun shouldLogServletException() {
        // Given
        `when`(response.status).thenReturn(500)

        val ex = ServletException("Error")
        doThrow(ex).`when`<FilterChain>(chain).doFilter(ArgumentMatchers.any(), ArgumentMatchers.any())

        try {
            // When
            filter.doFilter(request, response, chain)

            // Then
            fail()

        } catch (e: ServletException) {

            // Then
            verify<KVLogger>(kv).log(e)
        }

    }

    @Test
    @Throws(Exception::class)
    fun shouldLogRuntimeException() {
        // Given
        `when`(response.status).thenReturn(500)

        val ex = IllegalStateException("Error")
        doThrow(ex).`when`<FilterChain>(chain).doFilter(ArgumentMatchers.any(), ArgumentMatchers.any())

        try {
            // When
            filter.doFilter(request, response, chain)

            // Then
            fail()

        } catch (e: RuntimeException) {

            verify<KVLogger>(kv).log(e)
        }

    }

    @Test
    @Throws(Exception::class)
    fun shouldLogTracingHeaders() {
        // Given
        `when`(request.getHeader(TraceContext.TRACE_ID)).thenReturn("transaction-123")
        `when`(request.getHeader(TraceContext.CLIENT_ID)).thenReturn("client,123")
        `when`(request.getHeader(TraceContext.DEVICE_UID)).thenReturn("device,123")
        `when`(request.getHeader(TraceContext.MESSAGE_ID)).thenReturn("msg-123")
        `when`(request.getHeader(TraceContext.PARENT_MESSAGE_ID)).thenReturn("parent-msg-123")

        // When
        filter.doFilter(request, response, chain)

        // Then
        verify<KVLogger>(kv).add(TraceContext.TRACE_ID, "transaction-123")
        verify<KVLogger>(kv).add(TraceContext.CLIENT_ID, "client,123")
        verify<KVLogger>(kv).add(TraceContext.DEVICE_UID, "device,123")
        verify<KVLogger>(kv).add(TraceContext.MESSAGE_ID, "msg-123")
        verify<KVLogger>(kv).add(TraceContext.PARENT_MESSAGE_ID, "parent-msg-123")
    }


}

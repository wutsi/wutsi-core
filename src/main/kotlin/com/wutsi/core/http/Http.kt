package com.wutsi.core.http

import com.wutsi.core.exception.WutsiException
import com.wutsi.core.logging.KVLoggerImpl
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

open class Http(
    private val rest: RestTemplate,
    private val traceContextProvider: TraceContextProvider,
    private val exceptionHandler: HttpExceptionHandler
) {
    @Throws(RestClientException::class)
    fun <T> get(url: String, responseType: Class<T>, headers: Map<String, String>? = null): ResponseEntity<T> {
        return exchange(HttpMethod.GET, url, null, responseType, headers)
    }

    @Throws(RestClientException::class)
    fun <T> post(
        url: String,
        request: Any?,
        responseType: Class<T>?,
        headers: Map<String, String>? = null
    ): ResponseEntity<T> {
        return exchange(HttpMethod.POST, url, request, responseType, headers)
    }

    @Throws(RestClientException::class)
    fun delete(url: String, headers: Map<String, String>? = null) {
        exchange(HttpMethod.DELETE, url, null, Any::class.java, headers)
    }

    fun headers(): HttpHeaders {
        val trace = traceContextProvider.get()
        val headers = HttpHeaders()
        addHeader(TraceContext.CLIENT_ID, trace.clientId(), headers)
        addHeader(TraceContext.TRACE_ID, trace.traceId(), headers)
        addHeader(TraceContext.MESSAGE_ID, trace.messageId(), headers)
        addHeader(TraceContext.PARENT_MESSAGE_ID, trace.parentMessageId(), headers)
        addHeader(TraceContext.DEVICE_ID, trace.deviceUid(), headers)
        addHeader(TraceContext.USER_AGENT, trace.userAgent(), headers)
        return headers
    }

    @Throws(WutsiException::class)
    private fun <T> exchange(
        method: HttpMethod,
        url: String,
        request: Any?,
        responseType: Class<T>?,
        head: Map<String, String>? = null
    ): ResponseEntity<T> {

        val headers = headers()
        head?.forEach { addHeader(it.key, it.value, headers) }

        val now = System.currentTimeMillis()
        val entity = HttpEntity<Any>(request, headers)
        var exception: Throwable? = null
        var response: ResponseEntity<T>? = null
        try {

            response = rest.exchange(url, method, entity, responseType)
            return response

        } catch (ex: Exception) {

            exception = ex
            exceptionHandler.handleException(ex)
            throw IllegalStateException(ex)

        } finally {

            log(
                url = url,
                method = method,
                response = response,
                headers = headers,
                startDateTime = now,
                exception = exception
            )

        }
    }

    private fun <T> log(
        url: String,
        method: HttpMethod,
        response: ResponseEntity<T>?,
        headers: HttpHeaders,
        startDateTime: Long,
        exception: Throwable?
    ) {
        val logger = KVLoggerImpl()
        logger.add("HttpMethod", method.name)
        logger.add("URL", url)
        logger.add("HttpStatus", response?.statusCode)
        logger.add("Latency", System.currentTimeMillis() - startDateTime)
        logger.add("Success", exception == null)
        headers.keys.forEach { logger.add(it, headers[it]) }

        if (exception != null) {
            logger.add("Exception", exception.javaClass.name)
            logger.add("ExceptionMessage", exception.message)

            if (exception is HttpStatusCodeException) {
                val errorResponse = exceptionHandler.extractErrorResponse(exception)
                if (!errorResponse.error.code.isEmpty()) {
                    logger.add("HttpResponseErrorCode", errorResponse.error.code)
                }
            }
        }
        logger.log()
    }

    private fun addHeader(name: String, value: String?, headers: HttpHeaders) {
        if (value == null) {
            return
        }
        headers.put(name, listOf(value))
    }
}

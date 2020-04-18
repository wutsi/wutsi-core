package com.wutsi.core.logging

import com.wutsi.core.http.TraceContext
import java.io.IOException
import java.time.Clock
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class KVLoggerFilter(private val kv: KVLogger, var clock: Clock?) : Filter {
    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        // Empty
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {

        val startTime = clock!!.millis()
        try {

            filterChain.doFilter(servletRequest, servletResponse)
            log(startTime, (servletResponse as HttpServletResponse).status, servletRequest as HttpServletRequest, servletResponse, kv)
            kv.log()

        } catch (e: IOException) {
            log(startTime, 500, servletRequest as HttpServletRequest, servletResponse as HttpServletResponse, kv)
            kv.log(e)
            throw e
        } catch (e: ServletException) {
            log(startTime, 500, servletRequest as HttpServletRequest, servletResponse as HttpServletResponse, kv)
            kv.log(e)
            throw e
        } catch (e: RuntimeException) {
            log(startTime, 500, servletRequest as HttpServletRequest, servletResponse as HttpServletResponse, kv)
            kv.log(e)
            throw e
        }

    }

    override fun destroy() {
        // Empty
    }

    private fun log(
            startTime: Long,
            status: Int,
            request: HttpServletRequest,
            response: HttpServletResponse,
            kv: KVLogger
    ) {
        val latencyMillis = clock!!.millis() - startTime

        kv.add("Success", response.status / 100 == 2)
        kv.add("Latency", latencyMillis)

        kv.add("HttpRequestURI", request.requestURI)
        kv.add("HttpRequestEncoding", request.getHeader("Accept-Encoding"))
        kv.add("HttpRequestType", request.getHeader("Content-Type"))
        kv.add("HttpRequestAuthorization", request.getHeader("Authorization"))
        kv.add(TraceContext.TRACE_ID, request.getHeader(TraceContext.TRACE_ID))
        kv.add(TraceContext.MESSAGE_ID, request.getHeader(TraceContext.MESSAGE_ID))
        kv.add(TraceContext.CLIENT_ID, request.getHeader(TraceContext.CLIENT_ID))
        kv.add(TraceContext.PARENT_MESSAGE_ID, request.getHeader(TraceContext.PARENT_MESSAGE_ID))
        kv.add(TraceContext.DEVICE_UID, request.getHeader(TraceContext.DEVICE_UID))

        kv.add("HttpResponseStatus", status.toLong())
        kv.add("HttpResponseEncoding", response.getHeader("Content-Encoding"))
        kv.add("HttpResponseType", response.getHeader("Content-Type"))
        kv.add("HttpResponseLength", response.getHeader("Content-Length"))

        val params = request.parameterMap
        params.keys.forEach{ kv.add(it, params[it]?.toList()) }
    }
}

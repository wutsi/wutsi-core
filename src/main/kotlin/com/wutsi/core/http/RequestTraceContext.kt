package com.wutsi.core.http

import com.wutsi.core.tracking.DeviceUIDProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Service
import java.util.UUID
import javax.servlet.http.HttpServletRequest

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
open class RequestTraceContext(private val request: HttpServletRequest): TraceContext {
    companion object {
        const val DUID_COOKIE: String = DeviceUIDProvider.COOKIE_NAME
    }

    @Value("\${wutsi.http.client-id}")
    lateinit var clientId: String

    override fun clientId() = clientId

    override fun deviceUid(): String {
        // Get from header
        var id = request.getHeader(TraceContext.DEVICE_UID)

        // Get from attributes
        if (id == null){
            id = request.getAttribute(DUID_COOKIE)?.toString()
        }

        // Get from cookies
        if (id == null){
            val cookie = getCookie(DUID_COOKIE)
            id = cookie?.value
        }

        return if (id == null) "UNKOWN" else id
    }

    override fun userAgent() = request.getHeader(TraceContext.USER_AGENT)

    override fun traceId(): String {
        val id = request.getHeader(TraceContext.TRACE_ID)
        if (id == null){
            val savedId = request.getAttribute(TraceContext.TRACE_ID)
            if (savedId == null){
                val newId = UUID.randomUUID().toString()
                request.setAttribute(TraceContext.TRACE_ID, newId)
                return newId
            } else {
                return savedId.toString()
            }
        } else {
            return id
        }
    }

    override fun messageId() = UUID.randomUUID().toString()

    override fun parentMessageId() = request.getHeader(TraceContext.MESSAGE_ID)

    private fun getCookie(name: String) = request.cookies?.find { it.name == name }
}

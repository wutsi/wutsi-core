package com.wutsi.core.http

import org.springframework.context.ApplicationContext
import org.springframework.web.context.request.RequestContextHolder
import java.util.UUID

open class TraceContextProvider (
        private val clientId: String,
        private val context: ApplicationContext
){

    fun get(): TraceContext {
        if (RequestContextHolder.getRequestAttributes() != null) {
            return context.getBean(RequestTraceContext::class.java)
        } else {
            return DefaultTraceContext(clientId, UUID.randomUUID().toString())
        }
    }
}

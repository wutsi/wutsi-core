package com.wutsi.core.http

import java.util.UUID

open class DefaultTraceContext(
        private var clientId: String,
        private var traceId: String
): TraceContext {
    override fun clientId() = clientId
    override fun deviceUid() = null
    override fun traceId() = traceId
    override fun messageId() = UUID.randomUUID().toString()
    override fun parentMessageId() = null
}

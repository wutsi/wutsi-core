package com.wutsi.core.http

interface TraceContext {
    companion object {
        const val TRACE_ID = "X-Trace-ID"
        const val MESSAGE_ID = "X-Message-ID"
        const val PARENT_MESSAGE_ID = "X-Parent-Message-ID"
        const val CLIENT_ID = "X-Client-ID"
        const val DEVICE_ID = "X-Device-ID"
        const val USER_AGENT = "User-Agent"
    }

    fun clientId(): String
    fun deviceUid(): String?
    fun traceId(): String?
    fun messageId(): String?
    fun parentMessageId(): String?
    fun userAgent(): String?
}

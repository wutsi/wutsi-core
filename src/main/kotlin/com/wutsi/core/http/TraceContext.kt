package com.wutsi.core.http

interface TraceContext {
    companion object {
        const val TOKEN = "X-Token"
        const val TRACE_ID = "X-Trace-ID"
        const val MESSAGE_ID = "X-Message-ID"
        const val PARENT_MESSAGE_ID = "X-Parent-Message-ID"
        const val CLIENT_ID = "X-Client-ID"
        const val DEVICE_UID = "X-Device-UID"
    }
}

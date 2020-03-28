package com.wutsi.core.logging

import java.util.Optional

interface KVLogger {
    fun log()
    fun log(ex: Throwable)
    fun add(key: String, value: String?)
    fun add(key: String, value: Long?)
    fun add(key: String, value: Double?)
    fun add(key: String, value: Optional<Any>)
    fun add(key: String, values: Collection<Any>?)
    fun add(key: String, value: Any?)
}

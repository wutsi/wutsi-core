package com.wutsi.core.cache

interface CacheService{
    fun get(key: String): String?

    fun put(key: String, value: String, ttlSeconds: Int)

    fun remove(key: String)
}

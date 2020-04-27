package com.wutsi.core.cache

class NullCacheService: CacheService {
    override fun get(key: String): String?  = null

    override fun put(key: String, value: String, ttlSeconds: Int) {
    }

    override fun remove(key: String) {
    }
}

package com.wutsi.core.cache

import java.util.concurrent.ConcurrentHashMap

class HashMapCacheService: CacheService {
    private val data: ConcurrentHashMap<String, String> =  ConcurrentHashMap()

    override fun get(key: String): String? {
        return data[key]
    }

    override fun put(key: String, value: String, ttlSeconds: Int) {
        data[key] = value
    }

    override fun remove(key: String) {
        data.remove(key)
    }
}

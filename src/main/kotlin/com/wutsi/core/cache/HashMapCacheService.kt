package com.wutsi.core.cache

class HashMapCacheService: CacheService {
    private val data: MutableMap<String, String> = mutableMapOf()

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

package com.wutsi.core.url

interface UrlShortener {
    @Throws(UrlShortenerException::class)
    fun shorten(url: String): String
}

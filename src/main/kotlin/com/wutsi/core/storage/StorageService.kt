package com.wutsi.core.storage

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URL

interface StorageService {
    @Throws(IOException::class)
    fun store(path: String, content: InputStream, contentType: String? = null, ttlSeconds: Int? = null): URL

    @Throws(IOException::class)
    fun get(url: URL, os: OutputStream)

    fun visit(path: String, visitor: StorageVisitor)
}

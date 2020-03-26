package com.wutsi.core.service

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

interface StorageService {
    /**
     * Return the file URL
     */
    @Throws(IOException::class)
    fun store(path: String, content: InputStream, contentType: String? = null): String

    @Throws(IOException::class)
    fun get(url: String, os: OutputStream)
}

package com.wutsi.api.storage.service

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

interface StorageService {
    /**
     * Return the file URL
     */
    @Throws(IOException::class)
    fun store(storeId: Long, folder: String, filename: String, content: InputStream, contentType: String? = null): String

    @Throws(IOException::class)
    fun get(url: String, os: OutputStream)
}

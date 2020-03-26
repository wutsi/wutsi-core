package com.wutsi.core.service

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

open class LocalStorageService(
        private val directory: String,
        private val baseUrl: String
) : StorageService {
    companion object {
        const val BUF_SIZE = 1024
    }

    @Throws(IOException::class)
    override fun store(path: String, content: InputStream, contentType: String?): String {
        val f = File("$directory/$path")
        f.parentFile.mkdirs()

        FileOutputStream(f).use({ out ->
            content.copyTo(out, BUF_SIZE)
            return "${baseUrl}/$path"
        })
    }

    override fun get(url: String, os: OutputStream) {
        val path = url.substring(this.baseUrl.length)
        val file = File("$directory/$path")
        val fis = FileInputStream(file);
        fis.use {
            fis.copyTo(os, BUF_SIZE)
        }
    }
}

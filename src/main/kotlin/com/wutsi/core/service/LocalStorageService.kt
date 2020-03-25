package com.wutsi.api.storage.service

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

open class LocalStorageService : StorageService {
    companion object {
        const val BUF_SIZE = 1024
    }

    lateinit var directory: String
    lateinit var url: String

    @Throws(IOException::class)
    override fun store(storeId:Long, folder: String, filename: String, content: InputStream, contentType: String?): String {
        val path = path(storeId, folder, filename)
        val f = File("$directory/$path")
        f.parentFile.mkdirs()

        FileOutputStream(f).use({ out ->
            content.copyTo(out, BUF_SIZE)
            return "${url}/$path"
        })
    }

    override fun get(url: String, os: OutputStream) {
        val path = url.substring(this.url.length)
        val file = File("$directory/$path")
        val fis = FileInputStream(file);
        fis.use {
            fis.copyTo(os, BUF_SIZE)
        }
    }

    private fun path(storeId: Long, folder: String, filename: String) = String.format(
            "%s/%s/%s/%s",
            storeId,
            folder,
            UUID.randomUUID().toString(),
            filename
    )
}

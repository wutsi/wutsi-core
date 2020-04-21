package com.wutsi.core.storage

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URL

open class LocalStorageService(
        private val directory: String,
        private val baseUrl: String
) : StorageService {
    companion object {
        const val BUF_SIZE = 1024
    }

    @Throws(IOException::class)
    override fun store(path: String, content: InputStream, contentType: String?): URL {
        val f = toFile(path)
        f.parentFile.mkdirs()

        FileOutputStream(f).use({ out ->
            content.copyTo(out, BUF_SIZE)
            return toUrl(path)
        })
    }

    override fun get(url: URL, os: OutputStream) {
        val path = url.toString().substring(this.baseUrl.length)
        val file = File("$directory/$path")
        val fis = FileInputStream(file);
        fis.use {
            fis.copyTo(os, BUF_SIZE)
        }
    }

    override fun visit(path: String, visitor: StorageVisitor) {
        val file = toFile(path)
        visit(file, visitor)
    }

    private fun visit(file: File, visitor: StorageVisitor) {
        if (file.isFile) {
            visitor.visit(toUrl(file))
        } else {
            file.listFiles()?.forEach { visit(it, visitor) }
        }
    }

    private fun toFile(path: String) = File("$directory/$path")

    private fun toUrl (path: String) = URL("$baseUrl/$path")

    private fun toUrl (file: File): URL {
        val path = file.absolutePath.substring(directory.length+1)
        return toUrl(path)
    }
}

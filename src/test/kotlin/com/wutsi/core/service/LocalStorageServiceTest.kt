package com.wutsi.core.service

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

class LocalStorageServiceTest {
    private val storage = LocalStorageService("/tmp/wutsi", "http://localhost:999/storage")

    @After
    fun tearDown() {
        delete(File(System.getProperty("user.home") + "/tmp/wutsi/"))
    }

    @Test
    fun store() {
        val content = ByteArrayInputStream("hello".toByteArray())
        val result = storage.store("document/test.txt", content, "text/plain")

        assertNotNull(result)
        assertEquals("http://localhost:999/storage/document/test.txt", result)
    }


    @Test
    fun get() {
        val content = ByteArrayInputStream("hello world".toByteArray())
        val result = storage.store("document/test.txt2", content, "text/plain")

        val os = ByteArrayOutputStream()
        storage.get(result, os)

        assertEquals(os.toString(), "hello world")
    }

    private fun delete(file: File) {
        if (file.isDirectory) {
            val children = file.listFiles()
            if (children != null) {
                for (child in children) {
                    delete(child)
                }
            }
        }
        file.delete()
    }
}

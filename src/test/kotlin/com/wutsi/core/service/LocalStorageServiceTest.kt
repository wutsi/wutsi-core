package com.wutsi.core.service

import com.wutsi.api.storage.service.LocalStorageService
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

class LocalStorageServiceTest {
    private val storage = LocalStorageService()

    @Before
    fun setUp() {
        storage.directory = "/tmp/wutsi"
        storage.url = "http://localhost:999/storage"
    }

    @After
    fun tearDown() {
        delete(File(System.getProperty("user.home") + "/tmp/wutsi/"))
    }

    @Test
    fun store() {
        val content = ByteArrayInputStream("hello".toByteArray())
        val result = storage.store(100, "document", "test.txt", content, "text/plain")

        assertNotNull(result)
        assertTrue(result.startsWith("http://localhost:999/storage/100/document/"))
        assertTrue(result.endsWith("/test.txt"))
    }


    @Test
    fun get() {
        val content = ByteArrayInputStream("hello world".toByteArray())
        val result = storage.store(100, "document", "test2.txt", content, "text/plain")

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

package com.wutsi.core.storage

import com.wutsi.core.logging.KVLogger
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class StorageServlet(
        private val logger: KVLogger,
        private val directory: String
) : HttpServlet() {

    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val file = File("$directory${req.pathInfo}")
        output(file, resp)
    }

    private fun output(file: File, resp: HttpServletResponse) {
        val contentType = probeContentType(file)
        try {
            logger.add("File", file.absolutePath)
            logger.add("ContentType", contentType)

            resp.contentType = probeContentType(file)

            FileInputStream(file).use {
                `in` -> `in`.copyTo(resp.outputStream)
            }
        } catch (e: FileNotFoundException) {
            resp.sendError(404)
        }
    }

    private fun probeContentType(file: File): String {
        val i = file.absolutePath.lastIndexOf('.')
        val extension = if (i>0) file.absolutePath.substring(i+1) else ""
        if (extension == "jpg" || extension == "jpeg") {
            return "image/jpeg"
        } else if (extension == "png") {
            return "image/png"
        } else if (extension == "gif") {
            return "image/gif"
        }  else if (extension == "webp") {
            return "image/webp"
        } else {
            val contentType = Files.probeContentType(file.toPath())
            return if (contentType == null) "application/octet-stream" else contentType
        }
    }
}

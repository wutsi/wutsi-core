package com.wutsi.core.servlet

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


open class StorageServlet(val directory: String?) : HttpServlet() {

    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val path = req.pathInfo
        val file = File(directory!! + path)

        val contentType = Files.probeContentType(file.toPath())
        if (contentType != null) {
            resp.contentType = contentType
        }

        try {
            FileInputStream(file).use { `in` -> `in`.copyTo(resp.outputStream) }
        } catch (e: FileNotFoundException) {
            resp.sendError(404)
        }

    }
}

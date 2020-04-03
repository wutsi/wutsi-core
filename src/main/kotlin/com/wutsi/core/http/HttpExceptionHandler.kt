package com.wutsi.core.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.core.error.ErrorResponse
import com.wutsi.core.exception.BadRequestException
import com.wutsi.core.exception.ConflictException
import com.wutsi.core.exception.ForbiddenException
import com.wutsi.core.exception.InternalErrorException
import com.wutsi.core.exception.NotFoundException
import com.wutsi.core.exception.UnauthorizedException
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException

open class HttpExceptionHandler(private val objectMapper: ObjectMapper) {

    fun handleException(ex: Exception) {
        if (ex is HttpStatusCodeException){
            handleException(ex)
        } else {
            throw InternalErrorException(ex = ex)
        }
    }

    private fun handleException(exception: HttpStatusCodeException) {
        val response = extractErrorResponse(exception)
        val message = if (response.error.code.isEmpty()) exception.message else response.error.code

        if (exception.statusCode == HttpStatus.NOT_FOUND){
            throw NotFoundException(message, exception)
        } else if (exception.statusCode == HttpStatus.CONFLICT){
            throw ConflictException(message, exception)
        } else if (exception.statusCode == HttpStatus.UNAUTHORIZED){
            throw UnauthorizedException(message, exception)
        } else if (exception.statusCode == HttpStatus.BAD_REQUEST){
            throw BadRequestException(message, exception)
        } else if (exception.statusCode == HttpStatus.FORBIDDEN){
            throw ForbiddenException(message, exception)
        } else if (exception.statusCode == HttpStatus.INTERNAL_SERVER_ERROR){
            throw InternalErrorException(message, exception)
        } else {
            throw InternalErrorException(message, exception)
        }
    }

    private fun extractErrorResponse(ex: HttpStatusCodeException): ErrorResponse {
        try {
            return objectMapper.readValue(ex.responseBodyAsString, ErrorResponse::class.java)
        } catch (e: Exception){
            return ErrorResponse()
        }
    }
}

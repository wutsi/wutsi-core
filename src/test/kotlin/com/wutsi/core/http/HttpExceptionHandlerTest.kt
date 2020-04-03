package com.wutsi.core.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.core.error.ErrorDto
import com.wutsi.core.error.ErrorResponse
import com.wutsi.core.exception.BadRequestException
import com.wutsi.core.exception.ConflictException
import com.wutsi.core.exception.ForbiddenException
import com.wutsi.core.exception.InternalErrorException
import com.wutsi.core.exception.NotFoundException
import com.wutsi.core.exception.UnauthorizedException
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException

@RunWith(MockitoJUnitRunner::class)
class HttpExceptionHandlerTest {
    @Mock
    private lateinit var objectMapper: ObjectMapper

    @InjectMocks
    private lateinit var handler: HttpExceptionHandler

    @Test(expected = NotFoundException::class)
    fun handleNotFound () {
        val ex = createException(HttpStatus.NOT_FOUND, "foo")
        handler.handleException(ex)
    }

    @Test(expected = ForbiddenException::class)
    fun handleForbidden () {
        val ex = createException(HttpStatus.FORBIDDEN, "foo")
        handler.handleException(ex)
    }

    @Test(expected = UnauthorizedException::class)
    fun handleUnauthorized () {
        val ex = createException(HttpStatus.UNAUTHORIZED, "foo")
        handler.handleException(ex)
    }

    @Test(expected = ConflictException::class)
    fun handleConflict () {
        val ex = createException(HttpStatus.CONFLICT, "foo")
        handler.handleException(ex)
    }

    @Test(expected = BadRequestException::class)
    fun handleBadRequest () {
        val ex = createException(HttpStatus.BAD_REQUEST, "foo")
        handler.handleException(ex)
    }

    @Test(expected = InternalErrorException::class)
    fun handleInternalError () {
        val ex = createException(HttpStatus.INTERNAL_SERVER_ERROR, "foo")
        handler.handleException(ex)
    }


    @Test(expected = InternalErrorException::class)
    fun handleRuntimeException () {
        handler.handleException(RuntimeException("bad"))
    }

    private fun createException(status:HttpStatus, errorCode: String): HttpStatusCodeException {
        val json = "FOO"
        val ex = mock(HttpStatusCodeException::class.java)
        `when`(ex.responseBodyAsString).thenReturn(json)
        `when`(ex.statusCode).thenReturn(status)

        val response = ErrorResponse(error = ErrorDto(
                code = errorCode,
                message = "Error Message"
        ))
        `when`(objectMapper.readValue<ErrorResponse>(json, ErrorResponse::class.java)).thenReturn(response)

        return ex
    }
}

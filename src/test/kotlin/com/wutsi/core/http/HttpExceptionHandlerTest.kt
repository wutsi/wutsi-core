package com.wutsi.core.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.core.error.ErrorDto
import com.wutsi.core.error.ErrorResponse
import com.wutsi.core.exception.BadRequestException
import com.wutsi.core.exception.ConflictException
import com.wutsi.core.exception.ForbiddenException
import com.wutsi.core.exception.InternalErrorException
import com.wutsi.core.exception.NotFoundException
import com.wutsi.core.exception.UnauthorizedException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException

@ExtendWith(MockitoExtension::class)
class HttpExceptionHandlerTest {
    @Mock
    private lateinit var objectMapper: ObjectMapper

    @InjectMocks
    private lateinit var handler: HttpExceptionHandler

    @Test
    fun handleNotFound() {
        val ex = createException(HttpStatus.NOT_FOUND, "foo")
        assertThrows<NotFoundException> { handler.handleException(ex) }
    }

    @Test
    fun handleForbidden() {
        val ex = createException(HttpStatus.FORBIDDEN, "foo")
        assertThrows<ForbiddenException> { handler.handleException(ex) }
    }

    @Test
    fun handleUnauthorized() {
        val ex = createException(HttpStatus.UNAUTHORIZED, "foo")
        assertThrows<UnauthorizedException> { handler.handleException(ex) }
    }

    @Test
    fun handleConflict() {
        val ex = createException(HttpStatus.CONFLICT, "foo")
        assertThrows<ConflictException> { handler.handleException(ex) }
    }

    @Test
    fun handleBadRequest() {
        val ex = createException(HttpStatus.BAD_REQUEST, "foo")
        assertThrows<BadRequestException> { handler.handleException(ex) }
    }

    @Test
    fun handleInternalError() {
        val ex = createException(HttpStatus.INTERNAL_SERVER_ERROR, "foo")
        assertThrows<InternalErrorException> { handler.handleException(ex) }
    }

    @Test
    fun handleRuntimeException() {
        assertThrows<InternalErrorException> { handler.handleException(RuntimeException("bad")) }
    }

    private fun createException(status: HttpStatus, errorCode: String): HttpStatusCodeException {
        val json = "FOO"
        val ex = mock(HttpStatusCodeException::class.java)
        doReturn(json).whenever(ex).responseBodyAsString
        doReturn(status).whenever(ex).statusCode

        val response = ErrorResponse(
            error = ErrorDto(
                code = errorCode,
                message = "Error Message"
            )
        )
        doReturn(response).whenever(objectMapper).readValue(json, ErrorResponse::class.java)

        return ex
    }
}

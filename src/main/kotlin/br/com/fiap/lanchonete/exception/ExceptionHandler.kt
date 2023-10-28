package br.com.fiap.lanchonete.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    val log: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(BusinessException::class)
    fun businessExceptionHandler(businessException: BusinessException): ResponseEntity<ResponseErrorDto> {
        val responseErrorDto = businessException.exceptionEnum.getResponseError()
        responseErrorDto.messages = businessException.messages
        log.error("BusinessException: ${responseErrorDto.error} - ${responseErrorDto.messages}")
        return ResponseEntity(responseErrorDto,HttpStatusCode.valueOf(responseErrorDto.status))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationError(exception: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors = exception.bindingResult.fieldErrors.map { error ->
            "${error.field}: ${error.defaultMessage}"
        }

        val responseBody =ResponseErrorDto(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            messages = errors)

        return ResponseEntity(responseBody, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun unexpectedException(unexpectedException: Exception): ResponseEntity<ResponseErrorDto> {
        val responseBody = ResponseErrorDto(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            messages = listOf(unexpectedException.message ?: "An error occurred"))
        log.error("UnexpectedException: ${unexpectedException.cause} - ${unexpectedException.message}")
        return ResponseEntity(responseBody, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}
package br.com.fiap.lanchonete.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    val log: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(BusinessException::class)
    fun businessExceptionHandler(businessException: BusinessException): ResponseEntity<ResponseErrorDto> {
        val responseErrorDto = businessException.exceptionEnum.getResponseError()
        log.error(responseErrorDto.message)
        return ResponseEntity(responseErrorDto,HttpStatusCode.valueOf(responseErrorDto.status))
    }

}
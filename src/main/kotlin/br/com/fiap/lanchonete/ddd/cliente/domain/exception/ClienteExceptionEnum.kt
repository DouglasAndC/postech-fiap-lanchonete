package br.com.fiap.lanchonete.ddd.cliente.domain.exception

import br.com.fiap.lanchonete.exception.ResponseErrorDto
import br.com.fiap.lanchonete.exception.enums.ExceptionEnum
import org.springframework.http.HttpStatus

enum class ClienteExceptionEnum(private val error: String,
                                private val httpStatusCode: HttpStatus) : ExceptionEnum {
    CLIENTE_NOT_FOUND("Cliente nao foi encontrado", HttpStatus.NOT_FOUND),
    CLIENTE_ALREADY_EXISTS("Cliente ja cadastrado", HttpStatus.BAD_REQUEST);

    override fun getResponseError(): ResponseErrorDto {
        return ResponseErrorDto(error = error, status = httpStatusCode.value())
    }

}
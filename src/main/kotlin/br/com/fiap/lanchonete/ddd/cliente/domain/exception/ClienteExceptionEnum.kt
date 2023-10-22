package br.com.fiap.lanchonete.ddd.cliente.domain.exception

import br.com.fiap.lanchonete.exception.ResponseErrorDto
import br.com.fiap.lanchonete.exception.enums.ExceptionEnum
import org.springframework.http.HttpStatus

enum class ClienteExceptionEnum(val message: String, val httpStatusCode: HttpStatus) : ExceptionEnum {
    CLIENTE_NOT_FOUND("Cliente nao foi encontrado", HttpStatus.NOT_FOUND);

    override fun getResponseError(): ResponseErrorDto {
        return ResponseErrorDto(this.message, this.httpStatusCode.value())
    }

}
package br.com.fiap.lanchonete.ddd.cliente.domain.exception

import br.com.fiap.lanchonete.exception.dto.ResponseErrorDto
import br.com.fiap.lanchonete.exception.enums.ExceptionEnum
import org.springframework.http.HttpStatus

enum class ClienteExceptionEnum(private val error: String,
                                private val httpStatusCode: HttpStatus) : ExceptionEnum {

    CLIENTE_NOT_FOUND("Cliente não encontrado.", HttpStatus.NOT_FOUND),
    CLIENTE_ALREADY_EXISTS_CPF("CPF já cadastrado para outro cliente.", HttpStatus.BAD_REQUEST),
    CLIENTE_ALREADY_EXISTS_EMAIL("Email já cadastrado para outro cliente.", HttpStatus.BAD_REQUEST);

    override fun getResponseError(): ResponseErrorDto {
        return ResponseErrorDto(error = error, status = httpStatusCode.value())
    }

}
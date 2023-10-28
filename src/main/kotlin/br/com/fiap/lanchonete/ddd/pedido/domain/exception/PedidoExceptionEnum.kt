package br.com.fiap.lanchonete.ddd.pedido.domain.exception

import br.com.fiap.lanchonete.exception.dto.ResponseErrorDto
import br.com.fiap.lanchonete.exception.enums.ExceptionEnum
import org.springframework.http.HttpStatus

enum class PedidoExceptionEnum(private val error: String,
                                private val httpStatusCode: HttpStatus) : ExceptionEnum {

    PEDIDO_NOT_FOUND("Pedido não encontrado.", HttpStatus.NOT_FOUND);

    override fun getResponseError(): ResponseErrorDto {
        return ResponseErrorDto(error = error, status = httpStatusCode.value())
    }

}
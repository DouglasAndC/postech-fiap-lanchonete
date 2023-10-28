package br.com.fiap.lanchonete.ddd.produto.domain.model.exception

import br.com.fiap.lanchonete.exception.ResponseErrorDto
import br.com.fiap.lanchonete.exception.enums.ExceptionEnum
import org.springframework.http.HttpStatus

enum class ProdutoExceptionEnum(val error: String, val httpStatusCode: HttpStatus) : ExceptionEnum {
    PRODUTO_NOT_FOUND("Produto nao foi encontrado", HttpStatus.NOT_FOUND),
    CATEGORIA_NOT_FOUND("Categoria do produto não encontrada", HttpStatus.NOT_FOUND);

    override fun getResponseError(): ResponseErrorDto {
        return ResponseErrorDto(error = error, status = httpStatusCode.value())
    }

}
package br.com.fiap.lanchonete.exception.enums

import br.com.fiap.lanchonete.exception.ResponseErrorDto

interface ExceptionEnum {
    fun getResponseError(): ResponseErrorDto
}
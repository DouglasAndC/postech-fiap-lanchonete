package br.com.fiap.lanchonete.exception.enums

import br.com.fiap.lanchonete.exception.dto.ResponseErrorDto

interface ExceptionEnum {
    fun getResponseError(): ResponseErrorDto
}
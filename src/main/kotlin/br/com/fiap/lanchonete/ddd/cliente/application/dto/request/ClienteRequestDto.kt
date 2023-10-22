package br.com.fiap.lanchonete.ddd.cliente.application.dto.request

import jakarta.validation.constraints.Email

data class ClienteRequestDto(val cpf: String?,
                             val nome: String?,
                             @field:Email val email: String?)
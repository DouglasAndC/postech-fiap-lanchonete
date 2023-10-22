package br.com.fiap.lanchonete.ddd.cliente.application.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.br.CPF

data class ClienteRequestDto(@field:CPF @field:NotBlank val cpf: String?,
                             @field:NotBlank val nome: String?,
                             @field:Email @field:NotBlank val email: String?)
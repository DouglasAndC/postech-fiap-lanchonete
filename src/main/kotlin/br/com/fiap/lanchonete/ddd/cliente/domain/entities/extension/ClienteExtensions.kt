package br.com.fiap.lanchonete.ddd.cliente.domain.entities.extension

import br.com.fiap.lanchonete.ddd.cliente.application.dto.request.ClienteRequest
import br.com.fiap.lanchonete.ddd.cliente.application.dto.response.ClienteResponse
import br.com.fiap.lanchonete.ddd.cliente.domain.entities.Cliente

fun Cliente.toDTO() = ClienteResponse(cpf = cpf, nome = nome, email = email)
fun ClienteRequest.toEntity() = Cliente(id = null, cpf = cpf, nome = nome, email = email)

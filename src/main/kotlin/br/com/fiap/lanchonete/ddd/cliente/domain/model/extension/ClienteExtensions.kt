package br.com.fiap.lanchonete.ddd.cliente.domain.model.extension

import br.com.fiap.lanchonete.ddd.cliente.application.dto.request.ClienteRequestDto
import br.com.fiap.lanchonete.ddd.cliente.application.dto.response.ClienteResponseDto
import br.com.fiap.lanchonete.ddd.cliente.domain.model.Cliente

fun Cliente.toDTO(): ClienteResponseDto = ClienteResponseDto(cpf = cpf, nome = nome, email = email)
fun ClienteRequestDto.toEntity(): Cliente = Cliente(cpf = cpf, nome = nome, email = email)

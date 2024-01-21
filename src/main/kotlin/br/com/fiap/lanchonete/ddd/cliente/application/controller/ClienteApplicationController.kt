package br.com.fiap.lanchonete.ddd.cliente.application.controller

import br.com.fiap.lanchonete.ddd.cliente.application.dto.request.ClienteRequest
import br.com.fiap.lanchonete.ddd.cliente.application.dto.response.ClienteResponse
import br.com.fiap.lanchonete.ddd.cliente.domain.entities.extension.toDTO
import br.com.fiap.lanchonete.ddd.cliente.domain.entities.extension.toEntity
import br.com.fiap.lanchonete.ddd.cliente.domain.usecases.ClienteDomainUseCase
import org.springframework.stereotype.Service

@Service
class ClienteApplicationController(private val clienteDomainUseCase: ClienteDomainUseCase) {

    fun create(cliente: ClienteRequest): ClienteResponse =
        clienteDomainUseCase.create(cliente.toEntity()).toDTO()

    fun findByCpf(cpf: String): ClienteResponse =
        clienteDomainUseCase.findByCpf(cpf).toDTO()

}
package br.com.fiap.lanchonete.ddd.cliente.application.service

import br.com.fiap.lanchonete.ddd.cliente.application.dto.request.ClienteRequestDto
import br.com.fiap.lanchonete.ddd.cliente.application.dto.response.ClienteResponseDto
import br.com.fiap.lanchonete.ddd.cliente.domain.model.extension.toDTO
import br.com.fiap.lanchonete.ddd.cliente.domain.model.extension.toEntity
import br.com.fiap.lanchonete.ddd.cliente.domain.service.ClienteDomainService
import org.springframework.stereotype.Service

@Service
class ClienteApplicationService(private val clienteDomainService: ClienteDomainService) {

    fun create(cliente: ClienteRequestDto): ClienteResponseDto =
        clienteDomainService.create(cliente.toEntity()).toDTO()

    fun findByCpf(cpf: String): ClienteResponseDto =
        clienteDomainService.findByCpf(cpf).toDTO()

}
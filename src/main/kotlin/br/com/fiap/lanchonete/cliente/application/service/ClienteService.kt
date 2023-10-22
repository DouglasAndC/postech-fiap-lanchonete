package br.com.fiap.lanchonete.cliente.application.service

import br.com.fiap.lanchonete.cliente.application.dto.request.ClienteRequestDto
import br.com.fiap.lanchonete.cliente.application.dto.response.ClienteResponseDto
import br.com.fiap.lanchonete.cliente.domain.model.Cliente
import br.com.fiap.lanchonete.cliente.domain.model.extension.toDTO
import br.com.fiap.lanchonete.cliente.domain.model.extension.toEntity
import br.com.fiap.lanchonete.cliente.domain.repository.ClienteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ClienteService(private val clienteRepository: ClienteRepository) {

    @Transactional
    fun create(cliente: ClienteRequestDto): ClienteResponseDto {
        return clienteRepository.save(cliente.toEntity())?.toDTO() ?: throw Exception("Cliente erro de criacao")
    }

    fun findByCpf(cpf: String): ClienteResponseDto {
        return clienteRepository.findByCpf(cpf)?.toDTO() ?: throw Exception("Cliente erro de busca do cliente")
    }

    fun update(cliente: Cliente): Cliente {
        TODO("Not yet implemented")
    }

    fun delete(cliente: Cliente) {
        TODO("Not yet implemented")
    }
}
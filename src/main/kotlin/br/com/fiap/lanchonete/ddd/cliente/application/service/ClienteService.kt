package br.com.fiap.lanchonete.ddd.cliente.application.service

import br.com.fiap.lanchonete.ddd.cliente.application.dto.request.ClienteRequestDto
import br.com.fiap.lanchonete.ddd.cliente.application.dto.response.ClienteResponseDto
import br.com.fiap.lanchonete.ddd.cliente.domain.exception.ClienteExceptionEnum
import br.com.fiap.lanchonete.ddd.cliente.domain.model.extension.toDTO
import br.com.fiap.lanchonete.ddd.cliente.domain.model.extension.toEntity
import br.com.fiap.lanchonete.ddd.cliente.domain.repository.ClienteRepository
import br.com.fiap.lanchonete.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ClienteService(private val clienteRepository: ClienteRepository) {

    @Transactional
    fun create(cliente: ClienteRequestDto): ClienteResponseDto {

        cliente.cpf?.let {
            clienteRepository.findByCpf(it)
        }?.also {
            throw BusinessException(ClienteExceptionEnum.CLIENTE_ALREADY_EXISTS)
        }

        return clienteRepository.save(cliente.toEntity()).toDTO()
    }

    fun findByCpf(cpf: String) = clienteRepository.findByCpf(cpf)?.toDTO()
        ?: throw BusinessException(ClienteExceptionEnum.CLIENTE_NOT_FOUND)
}
package br.com.fiap.lanchonete.ddd.cliente.domain.service

import br.com.fiap.lanchonete.ddd.cliente.domain.exception.ClienteExceptionEnum
import br.com.fiap.lanchonete.ddd.cliente.domain.model.Cliente
import br.com.fiap.lanchonete.ddd.cliente.infrastructure.repository.ClienteJPARepository
import br.com.fiap.lanchonete.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClienteDomainService(private val clienteRepository: ClienteJPARepository) {

    @Transactional
    fun create(cliente: Cliente): Cliente {

        if(clienteExists(cliente) == true) {
            throw BusinessException(ClienteExceptionEnum.CLIENTE_ALREADY_EXISTS_CPF)
        }

        if(emailExists(cliente) == true){
            throw BusinessException(ClienteExceptionEnum.CLIENTE_ALREADY_EXISTS_EMAIL)
        }

        return clienteRepository.save(cliente)
    }

    fun findByCpf(cpf: String) = clienteRepository.findByCpf(cpf)
        ?: throw BusinessException(ClienteExceptionEnum.CLIENTE_NOT_FOUND)

    fun clienteExists(cliente: Cliente?) = cliente?.cpf?.let { clienteRepository.existsByCpf(it) }

    fun emailExists(cliente: Cliente) = cliente.email?.let { clienteRepository.existsByEmail(it) }

}
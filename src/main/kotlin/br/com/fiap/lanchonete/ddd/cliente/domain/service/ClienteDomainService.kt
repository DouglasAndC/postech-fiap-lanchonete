package br.com.fiap.lanchonete.ddd.cliente.domain.service

import br.com.fiap.lanchonete.ddd.cliente.domain.exception.ClienteExceptionEnum
import br.com.fiap.lanchonete.ddd.cliente.domain.model.Cliente
import br.com.fiap.lanchonete.ddd.cliente.domain.repository.ClienteRepository
import br.com.fiap.lanchonete.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ClienteDomainService(private val clienteRepository: ClienteRepository) {

    @Transactional
    fun create(cliente: Cliente): Cliente {

        if(cliente.cpf?.let { clienteRepository.existsByCpf(it) } == true){
            throw BusinessException(ClienteExceptionEnum.CLIENTE_ALREADY_EXISTS_CPF)
        }

        if(cliente.email?.let { clienteRepository.existsByEmail(it) } == true){
            throw BusinessException(ClienteExceptionEnum.CLIENTE_ALREADY_EXISTS_EMAIL)
        }

        return clienteRepository.save(cliente)
    }

    fun findByCpf(cpf: String) = clienteRepository.findByCpf(cpf)
        ?: throw BusinessException(ClienteExceptionEnum.CLIENTE_NOT_FOUND)
}
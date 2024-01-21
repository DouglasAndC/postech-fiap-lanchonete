package br.com.fiap.lanchonete.ddd.cliente.domain.usecases

import br.com.fiap.lanchonete.ddd.cliente.application.gateway.ClienteRepositoryGateway
import br.com.fiap.lanchonete.ddd.cliente.domain.entities.Cliente
import br.com.fiap.lanchonete.ddd.cliente.domain.exception.ClienteExceptionEnum
import br.com.fiap.lanchonete.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClienteDomainUseCase(private val clienteRepositoryGateway: ClienteRepositoryGateway) {

    @Transactional
    fun create(cliente: Cliente): Cliente {

        if(clienteExists(cliente) == true) {
            throw BusinessException(ClienteExceptionEnum.CLIENTE_ALREADY_EXISTS_CPF)
        }

        if(emailExists(cliente) == true){
            throw BusinessException(ClienteExceptionEnum.CLIENTE_ALREADY_EXISTS_EMAIL)
        }

        return clienteRepositoryGateway.save(cliente)
    }

    fun findByCpf(cpf: String) = clienteRepositoryGateway.findByCpf(cpf)
        ?: throw BusinessException(ClienteExceptionEnum.CLIENTE_NOT_FOUND)

    fun clienteExists(cliente: Cliente?) = cliente?.cpf?.let { clienteRepositoryGateway.existsByCpf(it) }

    fun emailExists(cliente: Cliente) = cliente.email?.let { clienteRepositoryGateway.existsByEmail(it) }

}
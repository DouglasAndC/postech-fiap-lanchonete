package br.com.fiap.lanchonete.ddd.cliente.application.gateway

import br.com.fiap.lanchonete.ddd.cliente.domain.entities.Cliente
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface ClienteRepositoryGateway {
    fun save(cliente: Cliente): Cliente
    fun findByCpf(cpf: String): Cliente?
    fun existsByCpf(cpf: String): Boolean
    fun existsByEmail(email: String): Boolean
}
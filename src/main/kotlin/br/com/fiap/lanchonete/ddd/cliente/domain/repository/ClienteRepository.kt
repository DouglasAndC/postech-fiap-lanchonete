package br.com.fiap.lanchonete.ddd.cliente.domain.repository

import br.com.fiap.lanchonete.ddd.cliente.domain.model.Cliente

interface ClienteRepository {
    fun save(cliente: Cliente): Cliente
    fun findByCpf(cpf: String): Cliente?
    fun existsByCpf(cpf: String): Boolean
    fun existsByEmail(email: String): Boolean
}
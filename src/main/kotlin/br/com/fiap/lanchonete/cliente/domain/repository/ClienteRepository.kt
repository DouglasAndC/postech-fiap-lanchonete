package br.com.fiap.lanchonete.cliente.domain.repository

import br.com.fiap.lanchonete.cliente.domain.model.Cliente

interface ClienteRepository {
    fun save(cliente: Cliente): Cliente?
    fun findByCpf(cpf: String): Cliente?
}
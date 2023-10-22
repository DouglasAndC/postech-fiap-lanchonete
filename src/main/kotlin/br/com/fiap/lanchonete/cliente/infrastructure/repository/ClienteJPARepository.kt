package br.com.fiap.lanchonete.cliente.infrastructure.repository

import br.com.fiap.lanchonete.cliente.domain.model.Cliente
import br.com.fiap.lanchonete.cliente.domain.repository.ClienteRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteJPARepository : ClienteRepository, JpaRepository<Cliente, Long>
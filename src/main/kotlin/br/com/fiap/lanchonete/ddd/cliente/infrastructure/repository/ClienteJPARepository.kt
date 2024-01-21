package br.com.fiap.lanchonete.ddd.cliente.infrastructure.repository

import br.com.fiap.lanchonete.ddd.cliente.domain.entities.Cliente
import br.com.fiap.lanchonete.ddd.cliente.domain.gateway.ClienteRepositoryGateway
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteJPARepository : ClienteRepositoryGateway, JpaRepository<Cliente, Long>
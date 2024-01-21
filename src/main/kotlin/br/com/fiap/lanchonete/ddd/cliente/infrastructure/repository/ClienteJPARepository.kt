package br.com.fiap.lanchonete.ddd.cliente.infrastructure.repository

import br.com.fiap.lanchonete.ddd.cliente.application.gateway.ClienteRepositoryGateway
import br.com.fiap.lanchonete.ddd.cliente.domain.entities.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteJPARepository : ClienteRepositoryGateway, JpaRepository<Cliente, Long>
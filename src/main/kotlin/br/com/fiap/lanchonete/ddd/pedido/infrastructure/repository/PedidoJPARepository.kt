package br.com.fiap.lanchonete.ddd.pedido.infrastructure.repository

import br.com.fiap.lanchonete.ddd.pedido.application.gateway.PedidoRepositoryGateway
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoJPARepository: PedidoRepositoryGateway, JpaRepository<Pedido, Long>
package br.com.fiap.lanchonete.ddd.pedido.infrastructure.repository

import br.com.fiap.lanchonete.ddd.cliente.domain.model.Cliente
import br.com.fiap.lanchonete.ddd.pedido.domain.repository.PedidoRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoJPARepository : PedidoRepository, JpaRepository<Cliente, Long>
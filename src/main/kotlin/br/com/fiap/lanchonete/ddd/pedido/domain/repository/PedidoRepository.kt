package br.com.fiap.lanchonete.ddd.pedido.domain.repository

import br.com.fiap.lanchonete.ddd.pedido.domain.model.Pedido
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PedidoRepository {
    fun save(pedido: Pedido): Pedido
    fun findAll(pageable: Pageable): Page<Pedido>
}
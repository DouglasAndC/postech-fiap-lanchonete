package br.com.fiap.lanchonete.ddd.pedido.domain.repository

import br.com.fiap.lanchonete.ddd.pedido.domain.model.Pedido

interface PedidoRepository {
    fun save(pedido: Pedido): Pedido
}
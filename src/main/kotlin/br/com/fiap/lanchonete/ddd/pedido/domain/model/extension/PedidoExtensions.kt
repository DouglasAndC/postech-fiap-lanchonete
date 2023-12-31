package br.com.fiap.lanchonete.ddd.pedido.domain.model.extension

import br.com.fiap.lanchonete.ddd.cliente.domain.model.extension.toDTO
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.ComboResponse
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoResponse
import br.com.fiap.lanchonete.ddd.pedido.domain.model.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.model.Combo
import br.com.fiap.lanchonete.ddd.produto.domain.model.extensions.toDTO

fun Pedido.toDTO() = PedidoResponse(
    id = this.id,
    status = this.status,
    cliente = this.cliente?.toDTO(),
    produtos = this.produtos.map { it.toDTO() }
)

fun Combo.toDTO() = ComboResponse(
    id = this.id,
    quantidade = this.quantidade,
    produto = this.produto.toDTO()
)
package br.com.fiap.lanchonete.ddd.pedido.domain.model.extension

import br.com.fiap.lanchonete.ddd.cliente.domain.model.extension.toDTO
import br.com.fiap.lanchonete.ddd.cliente.domain.model.extension.toEntity
import br.com.fiap.lanchonete.ddd.pedido.application.dto.request.PedidoProdutoRequest
import br.com.fiap.lanchonete.ddd.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoProdutoResponse
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoResponse
import br.com.fiap.lanchonete.ddd.pedido.domain.model.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.model.PedidoProduto
import br.com.fiap.lanchonete.ddd.produto.domain.model.extensions.toDTO
import br.com.fiap.lanchonete.ddd.produto.domain.model.extensions.toEntity

fun Pedido.toDTO() = PedidoResponse(
    id = this.id,
    status = this.status,
    cliente = this.cliente?.toDTO(),
    produtos = this.produtos.map { it.toDTO() }
)

fun PedidoRequest.toEntity() = Pedido(
    status = null,
    cliente = this.cliente?.toEntity(),
    produtos = this.produtos.map { it.toEntity() }.toMutableList()
)

fun PedidoProduto.toDTO() = PedidoProdutoResponse(
    id = this.id,
    quantidade = this.quantidade,
    produto = this.produto?.toDTO()
)

fun PedidoProdutoRequest.toEntity() = PedidoProduto(
    id = this.id,
    quantidade = this.quantidade,
    produto = this.produto?.toEntity()
)
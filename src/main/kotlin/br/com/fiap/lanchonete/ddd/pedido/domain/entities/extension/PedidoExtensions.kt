package br.com.fiap.lanchonete.ddd.pedido.domain.entities.extension

import br.com.fiap.lanchonete.ddd.cliente.domain.entities.extension.toDTO
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.ComboResponse
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoPagamentoStatusResponse
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoResponse
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Combo
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.ddd.produto.domain.entities.extensions.toDTO


fun Pedido.toStatusDTO() = PedidoPagamentoStatusResponse(
    id = this.id,
    pagamento = this.pagamento,
    createDate = this.createDate,
    updateDate = this.updateDate
)

fun Pedido.toDTO() = PedidoResponse(
    id = this.id,
    status = this.status,
    pagamento = this.pagamento,
    cliente = this.cliente?.toDTO(),
    produtos = this.produtos.map { it.toDTO() },
    createDate = this.createDate,
    updateDate = this.updateDate
)

fun Combo.toDTO() = ComboResponse(
    id = this.id,
    quantidade = this.quantidade,
    produto = this.produto.toDTO()
)
package br.com.fiap.lanchonete.ddd.pedido.application.dto.response

import br.com.fiap.lanchonete.ddd.cliente.application.dto.response.ClienteResponse
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPedido

data class PedidoResponse(
    val id: Long?,
    val status: StatusPedido?,
    val cliente: ClienteResponse?,
    val produtos: List<ComboResponse>)
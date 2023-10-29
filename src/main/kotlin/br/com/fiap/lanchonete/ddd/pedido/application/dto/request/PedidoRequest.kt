package br.com.fiap.lanchonete.ddd.pedido.application.dto.request

import br.com.fiap.lanchonete.ddd.cliente.application.dto.request.ClienteRequest
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class PedidoRequest(
    val cliente: ClienteRequest?,
    @field:NotNull @field:NotEmpty val produtos: List<PedidoProdutoRequest>)
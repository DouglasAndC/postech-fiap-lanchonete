package br.com.fiap.lanchonete.ddd.pedido.application.gateway

import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.client.dto.GetOrderResponse

interface PedidoGerarQrCodeGateway {
    fun generateQrCode(pedido: Pedido): String
    fun getOrder(orderId: String): GetOrderResponse
}
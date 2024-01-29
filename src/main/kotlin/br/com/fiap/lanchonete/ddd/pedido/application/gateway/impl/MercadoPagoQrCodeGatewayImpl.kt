package br.com.fiap.lanchonete.ddd.pedido.application.gateway.impl

import br.com.fiap.lanchonete.ddd.pedido.application.gateway.PedidoGerarQrCodeGateway
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.client.MercadoPagoClient
import br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.client.dto.GetOrderResponse
import br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.client.dto.extension.toGenerateQrCodeRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MercadoPagoQrCodeGatewayImpl(
    private val mercadoPagoClient: MercadoPagoClient,
    @Value("\${mercado-pago.collector-id}")
    private val collectorId : String,
    @Value("\${mercado-pago.bearer-token}")
    private val bearerToken : String,
    @Value("\${mercado-pago.pos-id}")
    private val posId : String) : PedidoGerarQrCodeGateway {

    override fun generateQrCode(pedido: Pedido): String {
        return mercadoPagoClient.generateQrCode(
            token = "Bearer ${this.bearerToken}",
            collectorId = collectorId,
            posId =  posId,
            request = pedido.toGenerateQrCodeRequest()
        ).qrData
    }

    override fun getOrder(orderId: String): GetOrderResponse {
        return mercadoPagoClient.getOrder(
            token = "Bearer ${this.bearerToken}",
            orderId = orderId
        )
    }
}
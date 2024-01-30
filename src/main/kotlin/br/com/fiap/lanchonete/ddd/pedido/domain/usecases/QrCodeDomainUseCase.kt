package br.com.fiap.lanchonete.ddd.pedido.domain.usecases

import br.com.fiap.lanchonete.ddd.pedido.application.gateway.PedidoGerarQrCodeGateway
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import org.springframework.stereotype.Service

@Service
class QrCodeDomainUseCase(private val qrCodeGateway: PedidoGerarQrCodeGateway) {

    fun generateQrCode(pedido: Pedido) = qrCodeGateway.generateQrCode(pedido)
    fun getOrder(orderId: String) = qrCodeGateway.getOrder(orderId)

}
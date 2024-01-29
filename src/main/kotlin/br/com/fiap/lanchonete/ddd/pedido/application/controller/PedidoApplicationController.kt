package br.com.fiap.lanchonete.ddd.pedido.application.controller

import br.com.fiap.lanchonete.ddd.cliente.domain.usecases.ClienteDomainUseCase
import br.com.fiap.lanchonete.ddd.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.ddd.pedido.application.dto.request.WebhookPedidoRequest
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoPagamentoStatusResponse
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoResponse
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Combo
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.extension.toDTO
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.extension.toStatusDTO
import br.com.fiap.lanchonete.ddd.pedido.domain.usecases.PedidoDomainUseCase
import br.com.fiap.lanchonete.ddd.pedido.domain.usecases.QrCodeDomainUseCase
import br.com.fiap.lanchonete.ddd.produto.domain.usecases.ProdutoDomainUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PedidoApplicationController(private val pedidoDomainUseCase: PedidoDomainUseCase,
                                  private val produtoDomainUseCase: ProdutoDomainUseCase,
                                  private val clienteDomainUseCase: ClienteDomainUseCase,
                                  private val qrCodeDomainUseCase: QrCodeDomainUseCase) {

    @Value("\${mercado-pago.enabled}") private val mercadoPagoEnabled: Boolean = false

    fun create(pedidoRequest: PedidoRequest): PedidoResponse? {

        val cliente = pedidoRequest.cliente?.cpf?.let { clienteDomainUseCase.findByCpf(it) }

        val pedido = Pedido(
            id = null,
            status = StatusPedido.RECEBIDO,
            cliente = cliente,
            produtos = emptyList<Combo>().toMutableList()
        )

        pedidoRequest.produtos.forEach{
            id -> pedido.addProduto(produtoDomainUseCase.get(id))
        }
        var qr = ""
        if(mercadoPagoEnabled){
            qr =  qrCodeDomainUseCase.generateQrCode(pedido)
        }
        return pedidoDomainUseCase.create(pedido)
            .toDTO(qr)
    }

    fun getAll(pageable: Pageable): Page<PedidoResponse> {
        val page = pedidoDomainUseCase.getAll(pageable)
        val pedidoDtos = page.content.map { it.toDTO() }
        return PageImpl(pedidoDtos, pageable, page.totalElements)
    }

    fun checkout(id: Long): PedidoResponse =
            pedidoDomainUseCase.checkout(id).toDTO()

    fun getStatusById(id: Long): PedidoPagamentoStatusResponse? =
        pedidoDomainUseCase.findPedidoById(id).toStatusDTO()

    fun updateStatus(id: Long, statusPedido: StatusPedido) =
        pedidoDomainUseCase.updateStatus(pedidoDomainUseCase.findPedidoById(id), statusPedido).toDTO()

    fun webhook(orderId: String, webhookPedidoRequest: WebhookPedidoRequest) {
        val pedido = pedidoDomainUseCase.findPedidoById(webhookPedidoRequest.data.id.toLong())
        if (mercadoPagoEnabled) {
            val orderResponse = qrCodeDomainUseCase.getOrder(orderId)
            orderResponse.let {
                pedidoDomainUseCase.closePedidoPagamento(pedido, it)
            }
        } else {
            pedidoDomainUseCase.closePedidoPagamento(pedido, null)
        }
    }

}
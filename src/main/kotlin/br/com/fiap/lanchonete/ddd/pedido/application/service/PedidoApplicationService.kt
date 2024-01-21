package br.com.fiap.lanchonete.ddd.pedido.application.service

import br.com.fiap.lanchonete.ddd.cliente.domain.service.ClienteDomainUseCase
import br.com.fiap.lanchonete.ddd.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoResponse
import br.com.fiap.lanchonete.ddd.pedido.domain.model.Combo
import br.com.fiap.lanchonete.ddd.pedido.domain.model.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.model.enums.StatusPedido
import br.com.fiap.lanchonete.ddd.pedido.domain.model.extension.toDTO
import br.com.fiap.lanchonete.ddd.pedido.domain.service.PedidoDomainService
import br.com.fiap.lanchonete.ddd.produto.domain.service.ProdutoDomainService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PedidoApplicationService(private val pedidoDomainService: PedidoDomainService,
                               private val produtoDomainService: ProdutoDomainService,
                               private val clienteDomainUseCase: ClienteDomainUseCase) {

    fun create(pedidoRequest: PedidoRequest): PedidoResponse? {

        val cliente = pedidoRequest.cliente?.cpf?.let { clienteDomainUseCase.findByCpf(it) }

        val pedido = Pedido(
            id = null,
            status = StatusPedido.RECEBIDO,
            cliente = cliente,
            produtos = emptyList<Combo>().toMutableList()
        )

        pedidoRequest.produtos.forEach{
            id -> pedido.addProduto(produtoDomainService.get(id))
        }

        return pedidoDomainService.create(pedido).toDTO()
    }

    fun getAll(pageable: Pageable): Page<PedidoResponse> {
        val page = pedidoDomainService.getAll(pageable)
        val pedidoDtos = page.content.map { it.toDTO() }
        return PageImpl(pedidoDtos, pageable, page.totalElements)
    }

    fun checkout(id: Long): PedidoResponse =
            pedidoDomainService.checkout(id).toDTO()

}
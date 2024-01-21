package br.com.fiap.lanchonete.ddd.pedido.application.controller

import br.com.fiap.lanchonete.ddd.cliente.domain.usecases.ClienteDomainUseCase
import br.com.fiap.lanchonete.ddd.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoResponse
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Combo
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.extension.toDTO
import br.com.fiap.lanchonete.ddd.pedido.domain.usecases.PedidoDomainUseCase
import br.com.fiap.lanchonete.ddd.produto.domain.service.ProdutoDomainService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PedidoApplicationController(private val pedidoDomainUseCase: PedidoDomainUseCase,
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

        return pedidoDomainUseCase.create(pedido).toDTO()
    }

    fun getAll(pageable: Pageable): Page<PedidoResponse> {
        val page = pedidoDomainUseCase.getAll(pageable)
        val pedidoDtos = page.content.map { it.toDTO() }
        return PageImpl(pedidoDtos, pageable, page.totalElements)
    }

    fun checkout(id: Long): PedidoResponse =
            pedidoDomainUseCase.checkout(id).toDTO()

}
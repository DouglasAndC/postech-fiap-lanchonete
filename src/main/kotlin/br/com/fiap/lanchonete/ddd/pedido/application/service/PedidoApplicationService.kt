package br.com.fiap.lanchonete.ddd.pedido.application.service

import br.com.fiap.lanchonete.ddd.cliente.domain.service.ClienteDomainService
import br.com.fiap.lanchonete.ddd.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoResponse
import br.com.fiap.lanchonete.ddd.pedido.domain.model.extension.toDTO
import br.com.fiap.lanchonete.ddd.pedido.domain.model.extension.toEntity
import br.com.fiap.lanchonete.ddd.pedido.domain.service.PedidoDomainService
import br.com.fiap.lanchonete.ddd.produto.domain.service.ProdutoDomainService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PedidoApplicationService(private val pedidoDomainService: PedidoDomainService,
                               private val produtoDomainService: ProdutoDomainService,
                               private val clienteDomainService: ClienteDomainService) {

    fun create(pedidoRequest: PedidoRequest): PedidoResponse? {

////        if(pedidoRequest.cliente?.let { ) } == false){
////            throw BusinessException(PedidoExceptionEnum.PEDIDO_NOT_FOUND)
////        }
//        clienteDomainService.clienteExists(pedidoRequest.cliente?.toEntity() ?: null)

//        pedidoRequest.produtos.map{ produtoDomainService.get(it.id)}

        return pedidoDomainService.create(pedidoRequest.toEntity()).toDTO()
    }

    fun getAll(pageable: Pageable): Page<PedidoResponse> {
        val page = pedidoDomainService.getAll(pageable)
        val pedidoDtos = page.content.map { it.toDTO() }
        return PageImpl(pedidoDtos, pageable, page.totalElements)
    }

}
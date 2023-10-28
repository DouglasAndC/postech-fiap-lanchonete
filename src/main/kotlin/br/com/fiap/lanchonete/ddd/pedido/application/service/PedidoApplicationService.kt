package br.com.fiap.lanchonete.ddd.pedido.application.service

import br.com.fiap.lanchonete.ddd.pedido.domain.service.PedidoDomainService
import org.springframework.stereotype.Service

@Service
class PedidoApplicationService(private val pedidoDomainService: PedidoDomainService) {

}
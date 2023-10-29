package br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.controller

import br.com.fiap.lanchonete.ddd.pedido.application.service.PedidoApplicationService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pedidos")
class PedidoController(private val pedidoApplicationService: PedidoApplicationService) {

}


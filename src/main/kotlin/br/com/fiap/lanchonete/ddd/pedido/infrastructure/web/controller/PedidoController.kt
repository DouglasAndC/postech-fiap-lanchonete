package br.com.fiap.lanchonete.ddd.pedido.infrastructure.web.controller

import br.com.fiap.lanchonete.ddd.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.ddd.pedido.application.dto.response.PedidoResponse
import br.com.fiap.lanchonete.ddd.pedido.application.service.PedidoApplicationService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/pedidos")
class PedidoController(private val pedidoApplicationService: PedidoApplicationService) {

    @PostMapping
    fun create(@Valid @RequestBody pedidoRequest: PedidoRequest,
               uriBuilder: UriComponentsBuilder
    ): ResponseEntity<PedidoResponse> {
        val pedidoCreated = pedidoApplicationService.create(pedidoRequest)
        val uri = uriBuilder.path("/api/v1/pedido/{id}").buildAndExpand(pedidoCreated?.id).toUri()
        return ResponseEntity.created(uri).body(pedidoCreated)
    }
    @GetMapping
    fun getAll(pageable: Pageable): ResponseEntity<Page<PedidoResponse>> =
        ResponseEntity(pedidoApplicationService.getAll(pageable), HttpStatus.OK)

    @PatchMapping("/{id}")
    fun checkout(@PathVariable(name = "id") id: Long): ResponseEntity<PedidoResponse> =
            ResponseEntity(pedidoApplicationService.checkout(id), HttpStatus.OK)


}


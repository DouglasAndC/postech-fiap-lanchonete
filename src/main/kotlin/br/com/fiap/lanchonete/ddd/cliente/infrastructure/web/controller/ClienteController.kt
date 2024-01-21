package br.com.fiap.lanchonete.ddd.cliente.infrastructure.web.controller

import br.com.fiap.lanchonete.ddd.cliente.application.controller.ClienteApplicationController
import br.com.fiap.lanchonete.ddd.cliente.application.dto.request.ClienteRequest
import br.com.fiap.lanchonete.ddd.cliente.application.dto.response.ClienteResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/clientes")
class ClienteController(private val clienteService: ClienteApplicationController) {
    @PostMapping
    fun create(@Valid @RequestBody cliente: ClienteRequest,
               uriBuilder: UriComponentsBuilder): ResponseEntity<ClienteResponse> {
        val clienteCreated = clienteService.create(cliente)
        val uri = uriBuilder.path("/api/v1/clientes/{cpf}").buildAndExpand(clienteCreated.cpf).toUri()
        return ResponseEntity.created(uri).body(clienteCreated)
    }

    @GetMapping("/{cpf}")
    fun findByCpf(@PathVariable(required = true) cpf: String) =
        ResponseEntity(clienteService.findByCpf(cpf = cpf), HttpStatus.OK)

}


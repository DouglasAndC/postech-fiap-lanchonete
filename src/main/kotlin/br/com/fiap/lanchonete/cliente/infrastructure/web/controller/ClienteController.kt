package br.com.fiap.lanchonete.cliente.infrastructure.web.controller

import br.com.fiap.lanchonete.cliente.application.dto.request.ClienteRequestDto
import br.com.fiap.lanchonete.cliente.application.dto.response.ClienteResponseDto
import br.com.fiap.lanchonete.cliente.application.service.ClienteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/clientes")
class ClienteController(private val clienteService: ClienteService) {
    @PostMapping
    fun create(@RequestBody cliente: ClienteRequestDto): ResponseEntity<ClienteResponseDto> {
        val clienteCriado = clienteService.create(cliente)
        return ResponseEntity(clienteCriado, HttpStatus.CREATED)
    }

    @GetMapping("/{cpf}")
    fun findByCpf(@PathVariable cpf: String): ResponseEntity<ClienteResponseDto>{
        val clienteCriado = clienteService.findByCpf(cpf = cpf)
        return ResponseEntity(clienteCriado, HttpStatus.CREATED)
    }
}


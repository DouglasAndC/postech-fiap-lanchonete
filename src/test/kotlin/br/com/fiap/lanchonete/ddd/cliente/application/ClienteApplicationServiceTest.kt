package br.com.fiap.lanchonete.ddd.cliente.application

import br.com.fiap.lanchonete.ddd.cliente.application.dto.request.ClienteRequest
import br.com.fiap.lanchonete.ddd.cliente.application.dto.response.ClienteResponse
import br.com.fiap.lanchonete.ddd.cliente.application.service.ClienteApplicationService
import br.com.fiap.lanchonete.ddd.cliente.domain.model.Cliente
import br.com.fiap.lanchonete.ddd.cliente.domain.model.extension.toEntity
import br.com.fiap.lanchonete.ddd.cliente.domain.service.ClienteDomainService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ClienteApplicationServiceTest {

    private val clienteDomainService = mock(ClienteDomainService::class.java)
    private val clienteApplicationService = ClienteApplicationService(clienteDomainService)

    @Test
    fun `teste criacao do cliente na layer application`() {
        val clienteRequestDto = ClienteRequest(
            cpf = "123.456.789-09",
            email = "email@email.com",
            nome = "Jonh Doe")
        val cliente = clienteRequestDto.toEntity()
        val clienteResponse = ClienteResponse(
            cpf = "123.456.789-09",
            email = "email@email.com",
            nome = "Jonh Doe")

        `when`(clienteDomainService.create(cliente)).thenReturn(cliente)

        val result = clienteApplicationService.create(clienteRequestDto)

        assertEquals(clienteResponse, result)
    }

    @Test
    fun `teste busca por cpf do cliente na layer application`() {
        val cpf = "123.456.789-09"
        val cliente = Cliente(
            id = 1,
            cpf = "123.456.789-09",
            email = "email@email.com",
            nome = "Jonh Doe")
        val clienteResponse = ClienteResponse(cpf = "123.456.789-09",
            email = "email@email.com",
            nome = "Jonh Doe")

        `when`(clienteDomainService.findByCpf(cpf)).thenReturn(cliente)

        val result = clienteApplicationService.findByCpf(cpf)

        assertEquals(clienteResponse, result)
    }
}
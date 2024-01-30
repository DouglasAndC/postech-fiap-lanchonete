package br.com.fiap.lanchonete.ddd.cliente.domain.service

import br.com.fiap.lanchonete.ddd.cliente.domain.exception.ClienteExceptionEnum
import br.com.fiap.lanchonete.ddd.cliente.domain.model.Cliente
import br.com.fiap.lanchonete.ddd.cliente.domain.repository.ClienteRepository
import br.com.fiap.lanchonete.exception.BusinessException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ClienteDomainServiceTest {

    @Mock
    lateinit var clienteRepository: ClienteRepository

    @InjectMocks
    lateinit var clienteDomainService: ClienteDomainService

    private val cliente = Cliente(1L, "123.456.789-09", "Cliente Teste", "cliente@teste.com")

    @Test
    fun `Deve criar cliente quando CPF e e-mail nao estao em uso`() {
        `when`(cliente.cpf?.let { clienteRepository.existsByCpf(it) }).thenReturn(false)
        `when`(cliente.email?.let { clienteRepository.existsByEmail(it) }).thenReturn(false)
        `when`(clienteRepository.save(cliente)).thenReturn(cliente)

        val result = clienteDomainService.create(cliente)

        assertEquals(cliente, result)
        verify(clienteRepository).save(cliente)
    }

    @Test
    fun `Deve lancar BusinessException quando cpf ja esta em uso`() {
        `when`(cliente.cpf?.let { clienteRepository.existsByCpf(it) }).thenReturn(true)

        val exception = assertThrows(BusinessException::class.java) {
            clienteDomainService.create(cliente)
        }

        assertEquals(ClienteExceptionEnum.CLIENTE_ALREADY_EXISTS_CPF, exception.exceptionEnum)
    }

    @Test
    fun `Deve lancar BusinessException quando email ja estao`() {
        `when`(cliente.cpf?.let { clienteRepository.existsByCpf(it) }).thenReturn(false)
        `when`(cliente.email?.let { clienteRepository.existsByEmail(it) }).thenReturn(true)

        val exception = assertThrows(BusinessException::class.java) {
            clienteDomainService.create(cliente)
        }

        assertEquals(ClienteExceptionEnum.CLIENTE_ALREADY_EXISTS_EMAIL, exception.exceptionEnum)
    }

    @Test
    fun `Deve buscar um cliente por cpf`() {
        val cpf = "123.456.789-09"
        `when`(clienteRepository.findByCpf(cpf)).thenReturn(cliente)

        val result = clienteDomainService.findByCpf(cpf)

        assertEquals(cliente, result)
    }

    @Test
    fun `Deve lancar BusinessException quando cliente nao for encontrado por cpf`() {
        val cpf = "987.654.321-09"
        `when`(clienteRepository.findByCpf(cpf)).thenReturn(null)

        val exception = assertThrows(BusinessException::class.java) {
            clienteDomainService.findByCpf(cpf)
        }

        assertEquals(ClienteExceptionEnum.CLIENTE_NOT_FOUND, exception.exceptionEnum)
    }
}
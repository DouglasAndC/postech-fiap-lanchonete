package br.com.fiap.lanchonete.ddd.pedido.domain.service

import br.com.fiap.lanchonete.ddd.cliente.domain.model.Cliente
import br.com.fiap.lanchonete.ddd.pedido.domain.model.Combo
import br.com.fiap.lanchonete.ddd.pedido.domain.model.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.model.enums.StatusPedido
import br.com.fiap.lanchonete.ddd.pedido.domain.repository.PedidoRepository
import br.com.fiap.lanchonete.exception.BusinessException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

@ExtendWith(MockitoExtension::class)
class PedidoDomainServiceTest {

    @InjectMocks
    lateinit var pedidoDomainService: PedidoDomainService

    @Mock
    lateinit var pedidoRepository: PedidoRepository

    private val cliente = Cliente(1L, "123.456.789-09", "Cliente Teste", "cliente@teste.com")

    private val pedido = Pedido(1L, StatusPedido.RECEBIDO ,cliente, emptyList<Combo>().toMutableList())


    @Test
    fun `test create should save and return pedido`() {
        `when`(pedidoRepository.save(pedido)).thenReturn(pedido)

        val result = pedidoDomainService.create(pedido)

        assertEquals(pedido, result)
        verify(pedidoRepository).save(pedido)
    }

    @Test
    fun `test getAll should return pageable pedidos`() {
        val pageable = PageRequest.of(0, 10)
        val pedidos = PageImpl(listOf(pedido))
        `when`(pedidoRepository.findAll(pageable)).thenReturn(pedidos)

        val result = pedidoDomainService.getAll(pageable)

        assertEquals(pedidos, result)
        verify(pedidoRepository).findAll(pageable)
    }

    @Test
    fun `deve alterar status para EM_PREPARACAO se o pedido estiver RECEBIDO`() {

        `when`(pedidoRepository.findPedidoById(pedido.id!!)).thenReturn(pedido)
        `when`(pedidoRepository.save(pedido)).thenReturn(pedido.copy(status = StatusPedido.RECEBIDO))

        val resultado = pedidoDomainService.checkout(pedido.id!!)

        assertsPedido(resultado)
    }

    @Test
    fun `deve lancar excecao se o status do pedido nao for RECEBIDO`() {

        `when`(pedidoRepository.findPedidoById(pedido.id!!)).thenReturn(pedido.copy(status = StatusPedido.EM_PREPARACAO))

        assertThrows <BusinessException> {
            pedidoDomainService.checkout(pedido.id!!)
        }
    }

    @Test
    fun `deve lancar excecao se o pedido nao for encontrado`() {

        `when`(pedidoRepository.findPedidoById(pedido.id!!)).thenReturn(null)

        assertThrows <BusinessException> {
            pedidoDomainService.checkout(pedido.id!!)
        }
    }

    private fun assertsPedido(response: Pedido) {
        assertEquals(response.id, pedido.id)
        assertEquals(response.produtos, pedido.produtos)
        assertEquals(response.cliente, pedido.cliente)
    }
}
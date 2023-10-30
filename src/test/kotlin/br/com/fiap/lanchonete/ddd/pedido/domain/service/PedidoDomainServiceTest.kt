package br.com.fiap.lanchonete.ddd.pedido.domain.service

import br.com.fiap.lanchonete.ddd.cliente.domain.model.Cliente
import br.com.fiap.lanchonete.ddd.pedido.domain.model.Combo
import br.com.fiap.lanchonete.ddd.pedido.domain.model.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.model.enums.StatusPedido
import br.com.fiap.lanchonete.ddd.pedido.domain.repository.PedidoRepository
import br.com.fiap.lanchonete.exception.BusinessException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PedidoDomainServiceTest {

    @InjectMocks
    lateinit var pedidoDomainService: PedidoDomainService

    @Mock
    lateinit var pedidoRepository: PedidoRepository

    private val cliente = Cliente(1L, "123.456.789-09", "Cliente Teste", "cliente@teste.com")

    private val pedido = Pedido(1L, StatusPedido.RECEBIDO ,cliente, emptyList<Combo>().toMutableList())

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
        Assertions.assertEquals(response.id, pedido.id)
        Assertions.assertEquals(response.produtos, pedido.produtos)
        Assertions.assertEquals(response.cliente, pedido.cliente)
    }
}
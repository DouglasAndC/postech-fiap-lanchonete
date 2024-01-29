package br.com.fiap.lanchonete.ddd.pedido.domain.usecases

import br.com.fiap.lanchonete.ddd.cliente.domain.entities.Cliente
import br.com.fiap.lanchonete.ddd.pedido.application.gateway.PedidoRepositoryGateway
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Combo
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPedido
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
class PedidoDomainUseCaseTest {

    @InjectMocks
    lateinit var pedidoDomainUseCase: PedidoDomainUseCase

    @Mock
    lateinit var pedidoRepositoryGateway: PedidoRepositoryGateway

    private val cliente = Cliente(1L, "123.456.789-09", "Cliente Teste", "cliente@teste.com")

    private val pedido = Pedido(1L, StatusPedido.RECEBIDO , StatusPagamento.AGUARDANDO_APROVACAO, cliente, emptyList<Combo>().toMutableList())


    @Test
    fun `test create should save and return pedido`() {
        `when`(pedidoRepositoryGateway.save(pedido)).thenReturn(pedido)

        val result = pedidoDomainUseCase.create(pedido)

        assertEquals(pedido, result)
        verify(pedidoRepositoryGateway).save(pedido)
    }

    @Test
    fun `test getAll should return pageable pedidos`() {
        val pageable = PageRequest.of(0, 10)
        val pedidos = PageImpl(listOf(pedido))
        `when`(pedidoRepositoryGateway.findAll(pageable)).thenReturn(pedidos)

        val result = pedidoDomainUseCase.getAll(pageable)

        assertEquals(pedidos, result)
        verify(pedidoRepositoryGateway).findAll(pageable)
    }

    @Test
    fun `deve alterar status para EM_PREPARACAO se o pedido estiver RECEBIDO`() {

        `when`(pedidoRepositoryGateway.findPedidoById(pedido.id!!)).thenReturn(pedido)
        `when`(pedidoRepositoryGateway.save(pedido)).thenReturn(pedido.copy(status = StatusPedido.RECEBIDO))

        val resultado = pedidoDomainUseCase.checkout(pedido.id!!)

        assertsPedido(resultado)
    }

    @Test
    fun `deve lancar excecao se o status do pedido nao for RECEBIDO`() {

        `when`(pedidoRepositoryGateway.findPedidoById(pedido.id!!)).thenReturn(pedido.copy(status = StatusPedido.EM_PREPARACAO))

        assertThrows <BusinessException> {
            pedidoDomainUseCase.checkout(pedido.id!!)
        }
    }

    @Test
    fun `deve lancar excecao se o pedido nao for encontrado`() {

        `when`(pedidoRepositoryGateway.findPedidoById(pedido.id!!)).thenReturn(null)

        assertThrows <BusinessException> {
            pedidoDomainUseCase.checkout(pedido.id!!)
        }
    }


    @Test
    fun `deve atualizar o status do pedido com sucesso`() {
        val pedidoId = 1L
        val novoStatus = StatusPedido.EM_PREPARACAO
        val pedido = pedido.copy(id = pedidoId)

        `when`(pedidoRepositoryGateway.save(pedido)).thenReturn(pedido)

        assertEquals(StatusPedido.RECEBIDO, pedido.status)
        assertEquals(novoStatus, pedidoDomainUseCase.updateStatus(pedido, novoStatus).status)
    }




    private fun assertsPedido(response: Pedido) {
        assertEquals(response.id, pedido.id)
        assertEquals(response.produtos, pedido.produtos)
        assertEquals(response.cliente, pedido.cliente)
    }
}
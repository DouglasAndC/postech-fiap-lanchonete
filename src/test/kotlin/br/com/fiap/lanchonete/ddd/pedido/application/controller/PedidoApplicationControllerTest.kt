package br.com.fiap.lanchonete.ddd.pedido.application.controller

import br.com.fiap.lanchonete.ddd.cliente.application.dto.request.ClienteRequest
import br.com.fiap.lanchonete.ddd.cliente.domain.entities.Cliente
import br.com.fiap.lanchonete.ddd.cliente.domain.usecases.ClienteDomainUseCase
import br.com.fiap.lanchonete.ddd.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Combo
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.extension.toStatusDTO
import br.com.fiap.lanchonete.ddd.pedido.domain.usecases.PedidoDomainUseCase
import br.com.fiap.lanchonete.ddd.produto.domain.entities.Produto
import br.com.fiap.lanchonete.ddd.produto.domain.entities.enums.CategoriaEnum
import br.com.fiap.lanchonete.ddd.produto.domain.usecases.ProdutoDomainUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class PedidoApplicationControllerTest {

    @Mock
    private lateinit var pedidoDomainUseCase: PedidoDomainUseCase

    @Mock
    private lateinit var produtoDomainUseCase: ProdutoDomainUseCase

    @Mock
    private lateinit var clienteDomainUseCase: ClienteDomainUseCase

    @InjectMocks
    private lateinit var pedidoApplicationController: PedidoApplicationController

    private val pedidoRequestValido = criarPedidoRequestValido()
    private val pedido = pedido()
    private val produto = produto()
    private val pedidoProduto = Combo(null, produto, pedido)

    @Test
    fun `deve criar pedido com sucesso`() {
        `when`(produtoDomainUseCase.get(1L)).thenReturn(produto)
        `when`(pedidoDomainUseCase.create(any())).thenReturn(
            pedido.copy(id = 1L, produtos = listOf(pedidoProduto).toMutableList())
        )

        val resultado = pedidoApplicationController.create(pedidoRequestValido)

        assertNotNull(resultado)
        assertEquals(pedido.id, resultado?.id)
        assertEquals(1, resultado?.produtos?.size)
    }

    @Test
    fun `deve buscar todos os pedidos`() {
        val pageable: Pageable = PageRequest.of(0, 10)
        val pedidoPage: Page<Pedido> = PageImpl(listOf(pedido))
        `when`(pedidoDomainUseCase.getAll(pageable)).thenReturn(pedidoPage)

        val resultado = pedidoApplicationController.getAll(pageable)

        assertNotNull(resultado)
        assertEquals(1, resultado.totalElements)
        assertEquals(pedido.id, resultado.content[0].id)
    }

    @Test
    fun `deve realizar checkout com sucesso`() {
        `when`(pedidoDomainUseCase.checkout(1L)).thenReturn(pedido)

        val resultado = pedidoApplicationController.checkout(1L)

        assertNotNull(resultado)
        assertEquals(pedido.id, resultado.id)
    }

    @Test
    fun `deve tratar pedido nao encontrado no checkout`() {
        `when`(pedidoDomainUseCase.checkout(1L)).thenReturn(null)

        val resultado = runCatching { pedidoApplicationController.checkout(1L) }

        assertTrue(resultado.isFailure)
    }

    @Test
    fun `deve criar pedido sem produtos`() {
        `when`(pedidoDomainUseCase.create(any())).thenReturn(pedido.copy(id = 1L, produtos = mutableListOf()))

        val resultado = pedidoApplicationController.create(criarPedidoRequestSemProdutos())

        assertNotNull(resultado)
        assertEquals(pedido.id, resultado?.id)
        assertTrue(resultado?.produtos.isNullOrEmpty())
    }

    @Test
    fun `deve tratar pedido nao encontrado durante checkout`() {
        `when`(pedidoDomainUseCase.checkout(1L)).thenReturn(null)

        val resultado = runCatching { pedidoApplicationController.checkout(1L) }

        assertTrue(resultado.isFailure)
    }

    @Test
    fun `deve buscar todos os pedidos corretamente`() {
        val pageable: Pageable = PageRequest.of(0, 10)
        val pedidoPage: Page<Pedido> = PageImpl(listOf(pedido))
        `when`(pedidoDomainUseCase.getAll(pageable)).thenReturn(pedidoPage)

        val resultado = pedidoApplicationController.getAll(pageable)

        assertNotNull(resultado)
        assertEquals(1, resultado.totalElements)
        assertEquals(pedido.id, resultado.content[0].id)
        verify(pedidoDomainUseCase).getAll(pageable)
    }

    private fun criarPedidoRequestSemProdutos(): PedidoRequest {
        return PedidoRequest(
            ClienteRequest("123.456.789-09", "João", "cliente@teste.com"),
            emptyList()
        )
    }

    @Test
    fun `deve retornar statusDTO quando pedido encontrado`() {
        val pedidoId = 1L
        val pedido = pedido().copy(id = pedidoId)
        `when`(pedidoDomainUseCase.findPedidoById(pedidoId)).thenReturn(pedido)

        val resultado = pedidoApplicationController.getStatusById(pedidoId)

        assertEquals(pedido.toStatusDTO(), resultado)
    }

    private fun criarPedidoRequestValido(): PedidoRequest {
        return PedidoRequest(
            ClienteRequest("123.456.789-09", "João", "cliente@teste.com"),
            listOf(1L)
        )
    }

    private fun pedido(): Pedido {
        return Pedido(
            1L,
            StatusPedido.RECEBIDO,
            StatusPagamento.AGUARDANDO_APROVACAO,
            Cliente(null, "123.456.789-09", "João", "cliente@teste.com"),
            emptyList<Combo>().toMutableList()
        )
    }

    private fun produto(): Produto {
        return Produto(
            1L,
            "Hamburguer",
            CategoriaEnum.LANCHE,
            "",
            BigDecimal.valueOf(10.0)
        )
    }
}

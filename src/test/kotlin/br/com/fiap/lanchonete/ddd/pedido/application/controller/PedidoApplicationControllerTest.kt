import br.com.fiap.lanchonete.ddd.cliente.application.dto.request.ClienteRequest
import br.com.fiap.lanchonete.ddd.cliente.domain.entities.Cliente
import br.com.fiap.lanchonete.ddd.pedido.application.controller.PedidoApplicationController
import br.com.fiap.lanchonete.ddd.pedido.application.dto.request.PedidoRequest
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Combo
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.Pedido
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.lanchonete.ddd.pedido.domain.usecases.PedidoDomainUseCase
import br.com.fiap.lanchonete.ddd.produto.domain.model.Produto
import br.com.fiap.lanchonete.ddd.produto.domain.model.enums.CategoriaEnum
import br.com.fiap.lanchonete.ddd.produto.domain.service.ProdutoDomainService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.anyOrNull
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class PedidoApplicationControllerTest {

    @Mock
    private lateinit var pedidoDomainUseCase: PedidoDomainUseCase

    @Mock
    private lateinit var produtoDomainService: ProdutoDomainService

    @InjectMocks
    private lateinit var pedidoApplicationController: PedidoApplicationController

    private var pedidoRequestValido =
        PedidoRequest(ClienteRequest("123.456.789-09","João", "cliente@teste.com"), listOf(1L))
    private var pedido = Pedido(1L, StatusPedido.RECEBIDO, Cliente(null, "123.456.789-09","João","cliente@teste.com"), emptyList<Combo>().toMutableList())
    private var produto = Produto(1L, "Hamburguer", CategoriaEnum.LANCHE,"", BigDecimal.valueOf(10.0))
    private var pedidoProduto = Combo(null, produto, pedido)

    @Test
    fun `deve criar pedido com sucesso`() {
        `when`(produtoDomainService.get(1L)).thenReturn(produto)
        `when`(pedidoDomainUseCase.create(anyOrNull())).thenReturn(pedido.copy(id = 1L, produtos = listOf(pedidoProduto).toMutableList()))

        val resultado = pedidoApplicationController.create(pedidoRequestValido)

        assertNotNull(resultado)
        assertEquals(pedido.id, resultado?.id)
        assertEquals(1, resultado?.produtos?.size)
    }

    @Test
    fun `deve buscar todos os pedidos`() {
        val pageable: Pageable = Mockito.mock(Pageable::class.java)
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
}

package br.com.fiap.lanchonete.ddd.produto.application.service

import br.com.fiap.lanchonete.ddd.produto.application.dto.request.ProdutoRequest
import br.com.fiap.lanchonete.ddd.produto.application.dto.response.ProdutoResponse
import br.com.fiap.lanchonete.ddd.produto.domain.model.Produto
import br.com.fiap.lanchonete.ddd.produto.domain.model.enums.CategoriaEnum
import br.com.fiap.lanchonete.ddd.produto.domain.service.ProdutoDomainService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class ProdutoApplicationServiceTest {

    @InjectMocks
    lateinit var produtoApplicationService: ProdutoApplicationService

    @Mock
    lateinit var produtoDomainService: ProdutoDomainService

    val produto: Produto = Produto(
            id = 1L,
            nome = "Hamburguer",
            descricao = "hamburguer de carne 180g",
            categoria = CategoriaEnum.LANCHE,
            preco = BigDecimal.TEN,
            imagens = emptyList()
    )

    val produtoRequest = ProdutoRequest(
            nome = produto.nome,
            descricao = produto.descricao,
            categoria = produto.categoria,
            preco = produto.preco
    )

    @Test
    fun `deve retornar DTO ao buscar por ID`() {

        `when`(produto.id?.let { produtoDomainService.get(it) }).thenReturn(produto)

        val resultado = produto.id?.let { produtoApplicationService.get(it) }

        if (resultado != null) {
            assertsProduto(resultado)
        }
    }

    @Test
    fun `deve criar e retornar DTO ao criar um produto`() {

        `when`(produtoDomainService.create(produto)).thenReturn(produto)

        val resultado = produtoApplicationService.create(produtoRequest)

        assertsProduto(resultado)
    }

    @Test
    fun `deve chamar metodo delete ao excluir um produto`() {
        val produtoId = 1L

        produtoApplicationService.delete(produtoId)

        verify(produtoDomainService).delete(produtoId)
    }

    @Test
    fun `deve retornar DTO ao atualizar um produto`() {


        `when`(produto.id?.let { produtoDomainService.put(it, produto) }).thenReturn(produto)

        val resultado = produto.id?.let { produtoApplicationService.put(it, produtoRequest) }

        if (resultado != null) {
            assertsProduto(resultado)
        }
    }

    @Test
    fun `deve retornar lista de DTOs ao buscar por categoria`() {
        val pageable = PageRequest.of(0, 10)
        val produtos = listOf(
                produto,
                produto.copy(id = 2)
        )
        val page = PageImpl(produtos, pageable, produtos.size.toLong())
        `when`(produtoDomainService.getByCategoria(CategoriaEnum.LANCHE, pageable)).thenReturn(page)

        val resultado = produtoApplicationService.getByCategoria(CategoriaEnum.LANCHE, pageable)

        assertEquals(2, resultado.totalElements)
    }

    @Test
    fun `deve chamar alterarImagem em ProdutoDomainService e converter para DTO`() {


        val imagens = listOf("imagem1.jpg", "imagem2.jpg")
        val produtoAlterado = produto.copy(imagens = imagens)

        `when`(produto.id?.let { produtoDomainService.alterarImagem(it, imagens) }).thenReturn(produtoAlterado)

        val resultado = produto.id?.let { produtoApplicationService.alterarImagem(it, imagens) }

        if (resultado != null) {
            assertEquals(resultado.imagens, imagens)
        }
    }

    private fun assertsProduto(response: ProdutoResponse) {
        assertEquals(response.id, produto.id)
        assertEquals(response.categoria, produto.categoria)
        assertEquals(response.nome, produto.nome)
        assertEquals(response.descricao, produto.descricao)
        assertEquals(response.preco, produto.preco)
        assertEquals(response.imagens, produto.imagens)
    }
}
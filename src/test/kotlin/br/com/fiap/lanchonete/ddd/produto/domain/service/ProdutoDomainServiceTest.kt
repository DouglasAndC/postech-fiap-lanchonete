package br.com.fiap.lanchonete.ddd.produto.domain.service

import br.com.fiap.lanchonete.ddd.produto.domain.model.Produto
import br.com.fiap.lanchonete.ddd.produto.domain.model.enums.CategoriaEnum
import br.com.fiap.lanchonete.ddd.produto.domain.repository.ProdutoRepository
import br.com.fiap.lanchonete.exception.BusinessException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class ProdutoDomainServiceTest {

    @Mock
    lateinit var produtoRepository: ProdutoRepository

    @InjectMocks
    lateinit var produtoDomainService: ProdutoDomainService

    val produto: Produto = Produto(
            id = 1L,
            nome = "Hamburguer",
            descricao = "hamburguer de carne 180g",
            categoria = CategoriaEnum.LANCHE,
            preco = BigDecimal.TEN,
            imagens = emptyList()
    )

    @Test
    fun `deve pegar produto`() {

        `when`(produtoRepository.findProdutoById(any(Long::class.java)))
                .thenReturn(produto)

        val resultado = produtoDomainService.get(1L)

        assertsProduto(resultado)

    }

    @Test
    fun `deve criar produto`() {
        `when`(produtoRepository.save(produto)).thenReturn(produto)

        val resultado = produtoDomainService.create(
                produto)

        verify(produtoRepository).save(produto)

        assertsProduto(resultado)

    }

    @Test
    fun `deletar produto existente deve ter sucesso`() {

        `when`(produto.id?.let { produtoRepository.findProdutoById(it) }).thenReturn(produto)

        produto.id?.let { produtoDomainService.delete(it) }

        verify(produtoRepository).delete(produto)
    }

    @Test
    fun `deletar produto nao existente deve lancar excecao`() {
        val nonExistingProductId = 2L

        `when`(produtoRepository.findProdutoById(nonExistingProductId)).thenReturn(null)

        assertThrows<BusinessException> {
            produtoDomainService.delete(nonExistingProductId)
        }
    }

    @Test
    fun `atualizar produto existente deve ter sucesso`() {

        `when`(produto.id?.let { produtoRepository.findProdutoById(it) }).thenReturn(produto)

        `when`(produtoRepository.save(produto)).thenReturn(produto)

        val resultado = produto.id?.let { produtoDomainService.put(it, produto) }

        if (resultado != null) {
            assertsProduto(resultado)
        }
    }

    @Test
    fun `atualizar produto nao existente deve lancar excecao`() {
        val idProdutoNaoExistente = 2L

        `when`(produtoRepository.findProdutoById(idProdutoNaoExistente)).thenReturn(null)


        assertThrows<BusinessException> {
            produtoDomainService.put(idProdutoNaoExistente, produto)
        }
    }

    @Test
    fun `getByCategoria com produtos existentes deve retornar lista de DTOs`() {
        val pageable = PageRequest.of(0, 10)
        val produtos = listOf(
               produto,
                produto.copy(id = 2)
        )
        val pageableProdutos = PageImpl(produtos, pageable, produtos.size.toLong())

        `when`(produtoRepository.findProdutoByCategoria(produto.categoria, pageable)).thenReturn(pageableProdutos)

        val resultado = produtoDomainService.getByCategoria(produto.categoria, pageable)

        assertEquals(2, resultado.totalElements)
    }

    @Test
    fun `getByCategoria com categoria nao encontrada deve lancar excecao`() {
        val categoriaNaoExistente = CategoriaEnum.SOBREMESA
        val pageable = PageRequest.of(0, 10)

        `when`(produtoRepository.findProdutoByCategoria(categoriaNaoExistente, pageable)).thenReturn(PageImpl(emptyList()))

        assertThrows<BusinessException> {
            produtoDomainService.getByCategoria(categoriaNaoExistente, pageable)
        }
    }

    @Test
    fun `deve alterar imagens do produto existente`() {

        val imagens = listOf("imagem1", "imagem2")

        `when`(produto.id?.let { produtoRepository.findProdutoById(it) }).thenReturn(produto)
        `when`(produtoRepository.save(produto)).thenReturn(produto.copy(imagens = imagens))

        val resultado = produto.id?.let { produtoDomainService.alterarImagem(it, imagens) }

        if (resultado != null) {
            assertsProduto(resultado)
        }
    }

    @Test
    fun `deve lancar excecao ao tentar alterar imagens de um produto inexistente`() {

        val imagens = listOf("imagem1", "imagem2")

        `when`(produto.id?.let { produtoRepository.findProdutoById(it) }).thenReturn(null)

        assertThrows<BusinessException> {
            produto.id?.let { produtoDomainService.alterarImagem(it, imagens) }
        }
    }

    private fun assertsProduto(response: Produto) {
        assertEquals(response.id, produto.id)
        assertEquals(response.categoria, produto.categoria)
        assertEquals(response.nome, produto.nome)
        assertEquals(response.descricao, produto.descricao)
        assertEquals(response.preco, produto.preco)
        assertEquals(response.imagens, produto.imagens)
    }
}
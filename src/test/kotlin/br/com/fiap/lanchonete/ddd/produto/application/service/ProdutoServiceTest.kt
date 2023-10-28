package br.com.fiap.lanchonete.ddd.produto.application.service

import br.com.fiap.lanchonete.ddd.produto.domain.model.Produto
import br.com.fiap.lanchonete.ddd.produto.domain.repository.ProdutoRepository
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito


class ProdutoServiceTest {

    @Mock
    lateinit var produtoRepository: ProdutoRepository

    lateinit var produtoService: ProdutoService

    @Test
    fun get() {

        Mockito.`when`(produtoRepository.findProdutoById(any(Long::class.java)))
                .thenReturn(Produto(
                        id = 1L,
                        nome = "Hamburguer",
                        descricao = "hamburguer de carne 180g",
                        categoria = ""
                ))

        val response = produtoService.get(1L)

        assert(response.id == 1L)

    }

    @Test
    fun create() {
    }

    @Test
    fun delete() {
    }

    @Test
    fun put() {
    }

    @Test
    fun getByCategoria() {
    }
}
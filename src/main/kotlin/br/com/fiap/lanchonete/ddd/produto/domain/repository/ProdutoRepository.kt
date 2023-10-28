package br.com.fiap.lanchonete.ddd.produto.domain.repository

import br.com.fiap.lanchonete.ddd.produto.domain.model.Produto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface ProdutoRepository {

    fun findProdutoById(id:Long): Produto?

    fun findProdutoByCategoria(categoria: String, pageable: Pageable): Page<Produto>

    fun save(produto: Produto): Produto

    fun delete(produto: Produto)

}
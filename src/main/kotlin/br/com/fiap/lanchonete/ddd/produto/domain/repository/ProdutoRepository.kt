package br.com.fiap.lanchonete.ddd.produto.domain.repository

import br.com.fiap.lanchonete.ddd.produto.domain.model.Produto
import br.com.fiap.lanchonete.ddd.produto.domain.model.enums.CategoriaEnum
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface ProdutoRepository {

    fun findProdutoById(id:Long): Produto?

    fun findProdutoByCategoria(categoria: CategoriaEnum, pageable: Pageable): Page<Produto>

    fun save(produto: Produto): Produto

    fun delete(produto: Produto)

}
package br.com.fiap.lanchonete.ddd.produto.application.gateway

import br.com.fiap.lanchonete.ddd.produto.domain.entities.Produto
import br.com.fiap.lanchonete.ddd.produto.domain.entities.enums.CategoriaEnum
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface ProdutoRepositoryGateway {

    fun findProdutoById(id:Long): Produto?

    fun findProdutoByCategoria(categoria: CategoriaEnum, pageable: Pageable): Page<Produto>

    fun save(produto: Produto): Produto

    fun delete(produto: Produto)

}
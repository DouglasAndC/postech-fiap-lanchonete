package br.com.fiap.lanchonete.produto.infrastructure.repository

import br.com.fiap.lanchonete.produto.domain.model.Produto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProdutoRepository : JpaRepository<Produto, Long> {

    fun findProdutoById(id:Long): Produto?
}
package br.com.fiap.lanchonete.ddd.produto.infrastructure.repository

import br.com.fiap.lanchonete.ddd.produto.domain.model.Produto
import br.com.fiap.lanchonete.ddd.produto.domain.repository.ProdutoRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProdutoJPARepository : ProdutoRepository, JpaRepository<Produto, Long> {

}
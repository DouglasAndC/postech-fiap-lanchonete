package br.com.fiap.lanchonete.ddd.produto.infrastructure.repository

import br.com.fiap.lanchonete.ddd.produto.application.gateway.ProdutoRepositoryGateway
import br.com.fiap.lanchonete.ddd.produto.domain.entities.Produto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProdutoJPARepository : ProdutoRepositoryGateway, JpaRepository<Produto, Long>
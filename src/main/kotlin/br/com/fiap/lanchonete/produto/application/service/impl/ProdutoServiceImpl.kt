package br.com.fiap.lanchonete.produto.application.service.impl

import br.com.fiap.lanchonete.produto.application.dto.request.ProdutoRequest
import br.com.fiap.lanchonete.produto.application.service.ProdutoService
import br.com.fiap.lanchonete.produto.domain.model.Produto
import br.com.fiap.lanchonete.produto.infrastructure.repository.ProdutoRepository

class ProdutoServiceImpl(
        private val produtoRepository: ProdutoRepository
)
    : ProdutoService  {

    override fun get(id: Long): Produto {

        return produtoRepository.findProdutoById(id) ?: throw Exception("produto não encontrado")

    }

    override fun create(produtoRequest: ProdutoRequest) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }
}
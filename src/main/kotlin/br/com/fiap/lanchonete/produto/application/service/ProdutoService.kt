package br.com.fiap.lanchonete.produto.application.service

import br.com.fiap.lanchonete.produto.application.dto.request.ProdutoRequest
import br.com.fiap.lanchonete.produto.domain.model.Produto
import org.springframework.stereotype.Service

@Service
interface ProdutoService {

    fun get(id: Long): ProdutoResponse

    fun create(produtoRequest: ProdutoRequest):Pr

    fun delete(id: Long)
}
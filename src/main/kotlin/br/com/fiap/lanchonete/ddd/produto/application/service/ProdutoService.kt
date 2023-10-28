package br.com.fiap.lanchonete.ddd.produto.application.service

import br.com.fiap.lanchonete.ddd.produto.application.dto.request.ProdutoRequest
import br.com.fiap.lanchonete.ddd.produto.domain.model.exception.ProdutoExceptionEnum
import br.com.fiap.lanchonete.ddd.produto.domain.model.extensions.toDTO
import br.com.fiap.lanchonete.ddd.produto.domain.model.extensions.toEntity
import br.com.fiap.lanchonete.ddd.produto.domain.repository.ProdutoRepository
import br.com.fiap.lanchonete.exception.BusinessException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProdutoService(
        private val produtoRepository: ProdutoRepository
) {

    fun get(id: Long) =
            produtoRepository.findProdutoById(id)?.toDTO()
                    ?: throw BusinessException(ProdutoExceptionEnum.PRODUTO_NOT_FOUND)


    fun create(produtoRequest: ProdutoRequest) = produtoRepository.save(produtoRequest.toEntity()).toDTO()

    fun delete(id: Long) = produtoRepository.findProdutoById(id)?.let {
        produtoRepository.delete(it)
    } ?: throw BusinessException(ProdutoExceptionEnum.PRODUTO_NOT_FOUND)

    fun put(id: Long, produtoRequest: ProdutoRequest) =
         produtoRepository.findProdutoById(id)?.let {
            produtoRepository.save(
                    it.copy(nome = produtoRequest.nome, categoria = produtoRequest.categoria, descricao = produtoRequest.descricao, imagens = produtoRequest.imagens))
                    .toDTO()
        } ?: throw BusinessException(ProdutoExceptionEnum.PRODUTO_NOT_FOUND)



    fun getByCategoria(categoria: String, pageable: Pageable) =
            produtoRepository.findProdutoByCategoria(categoria, pageable).also {
                if (it.isEmpty) throw BusinessException(ProdutoExceptionEnum.CATEGORIA_NOT_FOUND)
            }.map {
                it.toDTO()
            }
}
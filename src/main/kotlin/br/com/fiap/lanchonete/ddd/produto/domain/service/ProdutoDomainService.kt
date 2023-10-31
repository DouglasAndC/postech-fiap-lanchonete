package br.com.fiap.lanchonete.ddd.produto.domain.service

import br.com.fiap.lanchonete.ddd.produto.domain.model.Produto
import br.com.fiap.lanchonete.ddd.produto.domain.model.enums.CategoriaEnum
import br.com.fiap.lanchonete.ddd.produto.domain.model.exception.ProdutoExceptionEnum
import br.com.fiap.lanchonete.ddd.produto.domain.repository.ProdutoRepository
import br.com.fiap.lanchonete.exception.BusinessException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProdutoDomainService(
        private val produtoRepository: ProdutoRepository
) {
    fun get(id: Long) =
            produtoRepository.findProdutoById(id) ?:
            throw BusinessException(ProdutoExceptionEnum.PRODUTO_NOT_FOUND, messages = listOf("Produto com id=$id"))

    fun create(produto: Produto) = produtoRepository.save(produto)

    fun delete(id: Long) = produtoRepository.findProdutoById(id)?.let {
        produtoRepository.delete(it)
    } ?: throw BusinessException(ProdutoExceptionEnum.PRODUTO_NOT_FOUND, messages = listOf("Produto com id=$id"))

    fun put(id: Long, produto: Produto) =
         produtoRepository.findProdutoById(id)?.let {
            produtoRepository.save(it)
        } ?: throw BusinessException(ProdutoExceptionEnum.PRODUTO_NOT_FOUND, messages = listOf("Produto com id=$id"))

    fun getByCategoria(categoria: CategoriaEnum, pageable: Pageable) =
            produtoRepository.findProdutoByCategoria(categoria, pageable).also {
                if (it.isEmpty) throw BusinessException(ProdutoExceptionEnum.CATEGORIA_NOT_FOUND,
                    messages = listOf("Produto com categoria=$categoria"))
            }

    fun alterarImagem(id: Long, imagens: List<String>): Produto =
        produtoRepository.findProdutoById(id)?.let {
            it.imagens = imagens
            produtoRepository.save(it)
        } ?: throw BusinessException(ProdutoExceptionEnum.PRODUTO_NOT_FOUND, messages = listOf("Produto com id=$id"))

}
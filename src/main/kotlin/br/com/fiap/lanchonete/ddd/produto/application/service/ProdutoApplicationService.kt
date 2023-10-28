package br.com.fiap.lanchonete.ddd.produto.application.service

import br.com.fiap.lanchonete.ddd.produto.application.dto.request.ProdutoRequest
import br.com.fiap.lanchonete.ddd.produto.domain.model.enums.CategoriaEnum
import br.com.fiap.lanchonete.ddd.produto.domain.model.extensions.toDTO
import br.com.fiap.lanchonete.ddd.produto.domain.model.extensions.toEntity
import br.com.fiap.lanchonete.ddd.produto.domain.service.ProdutoDomainService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProdutoApplicationService(private val produtoDomainService: ProdutoDomainService
) {

    fun get(id: Long) = produtoDomainService.get(id).toDTO()

    fun create(produtoRequest: ProdutoRequest) = produtoDomainService.create(produtoRequest.toEntity()).toDTO()

    fun delete(id: Long) = produtoDomainService.delete(id)

    fun put(id: Long, produtoRequest: ProdutoRequest) =
        produtoDomainService.put(id,produtoRequest.toEntity()).toDTO()

    fun getByCategoria(categoria: CategoriaEnum, pageable: Pageable) =
           produtoDomainService.getByCategoria(categoria,pageable).map {
               it.toDTO()
           }
}
package br.com.fiap.lanchonete.ddd.produto.application.controller

import br.com.fiap.lanchonete.ddd.produto.application.dto.request.ProdutoRequest
import br.com.fiap.lanchonete.ddd.produto.domain.entities.enums.CategoriaEnum
import br.com.fiap.lanchonete.ddd.produto.domain.entities.extensions.toDTO
import br.com.fiap.lanchonete.ddd.produto.domain.entities.extensions.toEntity
import br.com.fiap.lanchonete.ddd.produto.domain.service.ProdutoDomainUseCase
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProdutoApplicationController(private val produtoDomainUseCase: ProdutoDomainUseCase
) {

    fun get(id: Long) = produtoDomainUseCase.get(id).toDTO()

    fun create(produtoRequest: ProdutoRequest) = produtoDomainUseCase.create(produtoRequest.toEntity()).toDTO()

    fun delete(id: Long) = produtoDomainUseCase.delete(id)

    fun put(id: Long, produtoRequest: ProdutoRequest) =
        produtoDomainUseCase.put(id,produtoRequest.toEntity()).toDTO()

    fun getByCategoria(categoria: CategoriaEnum, pageable: Pageable) =
           produtoDomainUseCase.getByCategoria(categoria,pageable).map {
               it.toDTO()
           }

    fun alterarImagem(id: Long, imagens: List<String>) =
            produtoDomainUseCase.alterarImagem(id,imagens).toDTO()

}
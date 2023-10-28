package br.com.fiap.lanchonete.ddd.produto.infrastructure.web.controller

import br.com.fiap.lanchonete.ddd.produto.application.dto.request.ProdutoRequest
import br.com.fiap.lanchonete.ddd.produto.application.dto.response.ProdutoResponse
import br.com.fiap.lanchonete.ddd.produto.application.service.ProdutoApplicationService
import br.com.fiap.lanchonete.ddd.produto.domain.model.enums.CategoriaEnum
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.util.UriComponentsBuilder

@Controller("/produto")
class ProdutoController(
        private val produtoApplicationService: ProdutoApplicationService,
) {

    @PostMapping
    fun create(@Valid @RequestBody produtoRequest: ProdutoRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<ProdutoResponse>{

        val produtoResponse = produtoApplicationService.create(produtoRequest)

        val uri = uriBuilder.path("/{id}").buildAndExpand(produtoResponse.id).toUri()

        return ResponseEntity.created(uri).build()
    }

    @GetMapping(" /{id}")
    fun getProduto(@PathVariable(name = "id") id: Long): ResponseEntity<ProdutoResponse> =
            ResponseEntity(produtoApplicationService.get(id), HttpStatus.OK)

    @PutMapping("/{id}")
    fun editProduto(@PathVariable(name = "id") id: Long, @Valid @RequestBody produtoRequest: ProdutoRequest): ResponseEntity<ProdutoResponse> =
            ResponseEntity(produtoApplicationService.put(id, produtoRequest), HttpStatus.OK)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(name = "id") id: Long): ResponseEntity<Unit> {

        produtoApplicationService.delete(id)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{categoria}/categoria")
    fun getByCategoria(@PathVariable(name = "categoria") categoria: CategoriaEnum, pageable: Pageable): ResponseEntity<Page<ProdutoResponse>> =
        ResponseEntity(produtoApplicationService.getByCategoria(categoria, pageable), HttpStatus.OK)

}
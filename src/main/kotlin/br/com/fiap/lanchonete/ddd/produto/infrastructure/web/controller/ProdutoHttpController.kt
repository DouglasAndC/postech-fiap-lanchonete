package br.com.fiap.lanchonete.ddd.produto.infrastructure.web.controller

import br.com.fiap.lanchonete.ddd.produto.application.controller.ProdutoApplicationController
import br.com.fiap.lanchonete.ddd.produto.application.dto.request.ImagemRequest
import br.com.fiap.lanchonete.ddd.produto.application.dto.request.ProdutoRequest
import br.com.fiap.lanchonete.ddd.produto.application.dto.response.ProdutoResponse
import br.com.fiap.lanchonete.ddd.produto.domain.entities.enums.CategoriaEnum
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/produto")
class ProdutoHttpController(
    private val produtoApplicationController: ProdutoApplicationController,
) {

    @PostMapping
    fun create(@Valid @RequestBody produtoRequest: ProdutoRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<ProdutoResponse>{

        val produtoResponse = produtoApplicationController.create(produtoRequest)

        val uri = uriBuilder.path("/lanchonete/api/v1/produto/{id}").buildAndExpand(produtoResponse.id).toUri()

        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/{id}")
    fun getProduto(@PathVariable(name = "id") id: Long): ResponseEntity<ProdutoResponse> =
            ResponseEntity(produtoApplicationController.get(id), HttpStatus.OK)

    @PutMapping("/{id}")
    fun editProduto(@PathVariable(name = "id") id: Long, @Valid @RequestBody produtoRequest: ProdutoRequest): ResponseEntity<ProdutoResponse> =
            ResponseEntity(produtoApplicationController.put(id, produtoRequest), HttpStatus.OK)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(name = "id") id: Long): ResponseEntity<Unit> {

        produtoApplicationController.delete(id)

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{categoria}/categoria")
    fun getByCategoria(@PathVariable(name = "categoria") categoria: CategoriaEnum, pageable: Pageable): ResponseEntity<Page<ProdutoResponse>> =
        ResponseEntity(produtoApplicationController.getByCategoria(categoria, pageable), HttpStatus.OK)

    @PatchMapping("/{id}/imagem")
    fun alterarImagem(@PathVariable(name = "id") id: Long, @Valid @RequestBody imagensRequest: ImagemRequest): ResponseEntity<ProdutoResponse> =
            ResponseEntity(produtoApplicationController.alterarImagem(id,imagensRequest.imagens), HttpStatus.OK)

}
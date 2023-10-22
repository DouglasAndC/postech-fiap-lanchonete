package br.com.fiap.lanchonete.produto.infrastructure.web.controller

import br.com.fiap.lanchonete.produto.application.service.ProdutoService
import br.com.fiap.lanchonete.produto.domain.model.Produto
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.net.URI

@Controller("/produto")
class ProdutoController(
        private val produtoService: ProdutoService
) {



    @GetMapping(" /{id}")
    fun getProduto(@PathVariable(name = "id") id: Long): ResponseEntity<Produto> {

        produtoService.get(id)


        return ResponseEntity.created(URI.create("")).build();
    }
}
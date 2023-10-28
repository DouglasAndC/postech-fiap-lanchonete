package br.com.fiap.lanchonete.ddd.produto.application.dto.response

import jakarta.validation.constraints.NotBlank


class ProdutoResponse(

        @field:NotBlank
        var id: Long,
        @field:NotBlank
        var nome: String,
        @field:NotBlank
        var categoria: String,
        @field:NotBlank
        var descricao: String,
        @field:NotBlank
        var imagens: List<String>
)
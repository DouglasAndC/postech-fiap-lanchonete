package br.com.fiap.lanchonete.ddd.produto.application.dto.request

import jakarta.validation.constraints.NotBlank


class ProdutoRequest(
        @field:NotBlank
        var nome: String,
        @field:NotBlank
        var categoria: String,
        @field:NotBlank
        var descricao: String,
        @field:NotBlank
        var imagens: MutableList<String>
)
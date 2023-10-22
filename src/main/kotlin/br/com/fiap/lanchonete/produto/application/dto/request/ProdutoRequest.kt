package br.com.fiap.lanchonete.produto.application.dto.request

import jakarta.validation.constraints.NotBlank


class ProdutoRequest(
        @NotBlank
        var nome: String,
        @NotBlank
        var categoria: String,
        @NotBlank
        var descricao: String,
        @NotBlank
        var imagens: MutableList<String>
)
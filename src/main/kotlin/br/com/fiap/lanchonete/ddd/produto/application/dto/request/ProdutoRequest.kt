package br.com.fiap.lanchonete.ddd.produto.application.dto.request

import br.com.fiap.lanchonete.ddd.produto.domain.entities.enums.CategoriaEnum
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal


class ProdutoRequest(
        @field:NotBlank
        var nome: String,
        @field:NotNull
        var categoria: CategoriaEnum,
        @field:NotBlank
        var descricao: String,
        @field:NotNull
        var preco: BigDecimal
)
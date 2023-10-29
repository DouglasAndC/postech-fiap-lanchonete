package br.com.fiap.lanchonete.ddd.produto.application.dto.request

import br.com.fiap.lanchonete.ddd.produto.domain.model.enums.CategoriaEnum
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
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
        var preco: BigDecimal,
        @field:NotEmpty
        var imagens: List<String>
)
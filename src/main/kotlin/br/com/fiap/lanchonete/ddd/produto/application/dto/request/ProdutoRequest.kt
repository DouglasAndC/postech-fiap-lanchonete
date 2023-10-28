package br.com.fiap.lanchonete.ddd.produto.application.dto.request

import br.com.fiap.lanchonete.ddd.produto.domain.model.enums.CategoriaEnum
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal


class ProdutoRequest(
        @field:NotBlank
        var nome: String,
        @field:NotBlank
        var categoria: CategoriaEnum,
        @field:NotBlank
        var descricao: String,
        @field:NotBlank
        var preco: BigDecimal,
        @field:NotBlank
        var imagens: List<String>
)
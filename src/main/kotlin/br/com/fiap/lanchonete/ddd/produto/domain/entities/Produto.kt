package br.com.fiap.lanchonete.ddd.produto.domain.entities

import br.com.fiap.lanchonete.ddd.produto.domain.entities.enums.CategoriaEnum
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import java.math.BigDecimal


@Entity
data class Produto(

        @SequenceGenerator(name = "produto_id_seq", sequenceName = "produto_id_seq",
                allocationSize = 1)
        @GeneratedValue(generator = "produto_id_seq", strategy = GenerationType.SEQUENCE)
        @Id
        val id: Long? = null,
        var nome: String = "",
        var categoria: CategoriaEnum,
        var descricao: String = "",
        var preco: BigDecimal,
        @ElementCollection
        @CollectionTable(name = "imagens_produto")
        @Column(name = "string", length = 10485760)
        var imagens: List<String> = emptyList()
)
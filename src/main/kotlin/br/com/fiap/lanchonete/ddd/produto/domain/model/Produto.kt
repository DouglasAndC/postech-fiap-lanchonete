package br.com.fiap.lanchonete.ddd.produto.domain.model

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator


@Entity
data class Produto(

        @SequenceGenerator(name = "produto_id_seq", sequenceName = "produto_id_seq",
                allocationSize = 1)
        @GeneratedValue(generator = "produto_id_seq", strategy = GenerationType.SEQUENCE)
        @Id
        val id: Long = 1,
        var nome: String = "",
        var categoria: String = "",
        var descricao: String = "",
        @ElementCollection
        var imagens: List<String> = emptyList()
)
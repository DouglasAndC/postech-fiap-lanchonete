package br.com.fiap.lanchonete.produto.domain.model

import jakarta.persistence.*


@Entity
class Produto(

        @SequenceGenerator(name = "produto_id_seq", sequenceName = "produto_id_seq",
                allocationSize = 1)
        @GeneratedValue(generator = "produto_id_seq", strategy = GenerationType.SEQUENCE)
        @Id
        val id: Long = 1,
        var nome: String = "",
        var categoria: String = "",
        var descricao: String = "",
        var imagens: String = ""
)
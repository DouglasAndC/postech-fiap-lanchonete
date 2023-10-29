package br.com.fiap.lanchonete.ddd.pedido.domain.model

import br.com.fiap.lanchonete.ddd.produto.domain.model.Produto
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator

@Entity
data class PedidoProduto(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_produto_id_seq")
    @SequenceGenerator(name = "pedido_produto_id_seq", sequenceName = "pedido_produto_id_seq", allocationSize = 1)
    var id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    var produto: Produto? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    var pedido: Pedido? = null,
    var quantidade: Int? = null
)

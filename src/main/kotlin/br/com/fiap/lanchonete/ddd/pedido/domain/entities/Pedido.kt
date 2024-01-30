package br.com.fiap.lanchonete.ddd.pedido.domain.entities

import br.com.fiap.lanchonete.ddd.cliente.domain.entities.Cliente
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPagamento
import br.com.fiap.lanchonete.ddd.pedido.domain.entities.enums.StatusPedido
import br.com.fiap.lanchonete.ddd.produto.domain.entities.Produto
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Pedido(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_id_seq")
    @SequenceGenerator(name = "pedido_id_seq", sequenceName = "pedido_id_seq", allocationSize = 1)
    var id: Long? = null,
    @Enumerated(EnumType.STRING)
    var status: StatusPedido?,
    @Enumerated(EnumType.ORDINAL)
    var pagamento: StatusPagamento = StatusPagamento.AGUARDANDO_APROVACAO,
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    val cliente: Cliente? = null,
    @OneToMany(mappedBy = "pedido", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var produtos: MutableList<Combo> = mutableListOf(),
    @Column(precision = 15, scale = 2)
    var precoTotal: BigDecimal = BigDecimal.ZERO,
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    var createDate: LocalDateTime? = null,
    @LastModifiedDate
    @Column(name = "updated_date", nullable = false, updatable = false)
    var updateDate: LocalDateTime? = null
){
    fun addProduto(produto: Produto) {
        val pedidoProdutoExistente = produtos.find { it.produto.id == produto.id }

        if (pedidoProdutoExistente != null) {
            pedidoProdutoExistente.incrementarQuantidade()
        } else {
            val novoCombo = Combo(pedido = this, produto = produto)
            produtos.add(novoCombo)
        }

        recalcularPrecoTotal()
    }

    private fun recalcularPrecoTotal() {
        precoTotal = produtos.sumOf { it.calcularPrecoTotal() }
    }

    override fun toString(): String {
        return "Pedido(id=$id, cliente=$cliente, ,precoTotal=$precoTotal, produtos=${produtos})"
    }

}
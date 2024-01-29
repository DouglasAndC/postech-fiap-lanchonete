package br.com.fiap.lanchonete.ddd.pedido.domain.entities

import br.com.fiap.lanchonete.ddd.produto.domain.entities.Produto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Combo(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "combo_id_seq")
    @SequenceGenerator(name = "combo_id_seq", sequenceName = "combo_id_seq", allocationSize = 1)
    var id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    var produto: Produto,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    var pedido: Pedido,
    var quantidade: Int = 1,
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    var createDate: LocalDateTime? = null,
    @LastModifiedDate
    @Column(name = "updated_date", nullable = false, updatable = false)
    var updateDate: LocalDateTime? = null
){
    fun incrementarQuantidade() {
        quantidade += 1
    }

    fun calcularPrecoTotal(): BigDecimal {
        return produto.preco.multiply(quantidade.toBigDecimal())
    }

    override fun toString(): String {
        return "Combo(id=$id, produto=${produto.id}, pedido=${pedido.id}, quantidade=$quantidade, " +
                "createDate=$createDate, updateDate=$updateDate)"
    }
}

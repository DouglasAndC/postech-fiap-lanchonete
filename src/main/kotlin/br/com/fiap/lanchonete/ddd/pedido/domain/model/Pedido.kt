package br.com.fiap.lanchonete.ddd.pedido.domain.model

import br.com.fiap.lanchonete.ddd.cliente.domain.model.Cliente
import br.com.fiap.lanchonete.ddd.pedido.domain.model.enums.StatusPedido
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class Pedido(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_id_seq")
    @SequenceGenerator(name = "pedido_id_seq", sequenceName = "pedido_id_seq", allocationSize = 1)
    var id: Long?,
    val status: StatusPedido?,
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    val cliente: Cliente?,
    @OneToMany(mappedBy = "pedido", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var produtos: MutableList<PedidoProduto> = mutableListOf()
)
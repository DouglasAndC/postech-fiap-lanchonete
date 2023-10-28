package br.com.fiap.lanchonete.ddd.pedido.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class Pedido(
    @Id
    @Column(name = "uuid", unique = true)
    val uuid: UUID)
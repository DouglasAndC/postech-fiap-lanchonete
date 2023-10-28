package br.com.fiap.lanchonete.ddd.cliente.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.validation.constraints.Email

@Entity
data class Cliente(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_id_seq")
    @SequenceGenerator(name = "cliente_id_seq", sequenceName = "cliente_id_seq", allocationSize = 1)
    val id: Long?,
    @Column(name = "CPF", unique = true)
    val cpf: String?,
    @Column(name = "NOME")
    val nome: String?,
    @Email
    @Column(name = "EMAIL", unique = true)
    val email: String?)
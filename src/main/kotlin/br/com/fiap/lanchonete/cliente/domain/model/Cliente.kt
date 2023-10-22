package br.com.fiap.lanchonete.cliente.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.validation.constraints.Email
import org.hibernate.validator.constraints.br.CPF

@Entity
data class Cliente(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_id_seq")
    @SequenceGenerator(name = "cliente_id_seq", sequenceName = "cliente_id_seq", allocationSize = 1)
    val id: Long,
    @CPF
    @Column(name = "CPF", unique = true)
    val cpf: String?,
    @Column(name = "NOME")
    val nome: String?,
    @Email
    @Column(name = "EMAIL", unique = true)
    val email: String?){
    constructor(cpf: String?, nome: String?, email: String?) : this(
        id = 0,
        cpf = cpf,
        nome = nome,
        email = email)
}
package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "alunos")
data class AlunoEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val nome: String,
    @Column(unique = true, nullable = false)
    val email: String,

    val telefone: String,

    @Column(unique = true, nullable = false)
    val cpf: String,
    val plano: String,
    val status: Boolean,

    val treinoAtual: String?,
    val focoTreino: String?,
    val planoVencimento: String?,
    val planoValor: Double?
)
package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "planos")
class PlanoEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false, length = 500)
    val descricao: String,

    @Column(nullable = false)
    val valor: Double,

    @Column(name = "duracao_meses", nullable = false)
    val duracaoMeses: Int,

    @Column(nullable = false)
    val ativo: Boolean
)
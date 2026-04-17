package com.academia.smartgym.domain.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "planos")
data class Plano(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var nome: String,

    @Column(nullable = false)
    var ativo: Boolean,

    @Column(nullable = false)
    var dataFimPromocao: LocalDate,

    @Column(nullable = false)
    var horarioLimiteAcesso: LocalTime
)
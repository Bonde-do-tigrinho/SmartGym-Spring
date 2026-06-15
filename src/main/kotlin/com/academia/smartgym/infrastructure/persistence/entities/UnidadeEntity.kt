package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*

@Entity
@Table(name = "unidades")
data class UnidadeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false)
    val endereco: String,

    @Column(nullable = false)
    val cidade: String,

    @Column(nullable = false)
    val ativa: Boolean = true
)
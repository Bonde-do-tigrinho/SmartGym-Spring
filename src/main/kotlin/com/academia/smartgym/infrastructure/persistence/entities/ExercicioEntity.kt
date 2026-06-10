package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*

@Entity
@Table(name = "exercicios")
class ExercicioEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false, length = 500)
    val descricao: String,

    @Column(nullable = false)
    val tipo: String,

    @Column(nullable = false)
    val grupoMuscular: String?,

    @Column(name = "maquina_id")
    val maquinaId: Int? = null // NULL se tipo for LIVRE
)


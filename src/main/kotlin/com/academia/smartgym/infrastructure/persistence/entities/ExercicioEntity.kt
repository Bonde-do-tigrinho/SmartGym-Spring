package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*

@Entity
@Table(name = "exercicios")
class ExercicioEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false, length = 500)
    val descricao: String,

    @Column(nullable = false)
    val tipo: String = "LIVRE", // Armazena como String: "LIVRE" ou "MAQUINA"

    @Column(name = "maquina_id")
    val maquinaId: Long? = null // NULL se tipo for LIVRE
)


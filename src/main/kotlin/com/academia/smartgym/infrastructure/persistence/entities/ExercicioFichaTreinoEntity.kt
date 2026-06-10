package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*

@Entity
@Table(name = "ficha_treino_exercicios")
class ExercicioFichaTreinoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "exercicio_id", nullable = false)
    val exercicioId: Int,

    @Column(nullable = false)
    val series: Int,

    @Column(nullable = false)
    val repeticoes: Int,

    @Column(name = "descanso_segundos", nullable = false)
    val descansoSegundos: Int,

    @Column(name = "treino_dia_id")
    var treinoDiaId: Int? = null
)
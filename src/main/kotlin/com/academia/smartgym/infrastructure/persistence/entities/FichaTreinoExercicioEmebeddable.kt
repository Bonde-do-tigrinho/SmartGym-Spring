package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class FichaTreinoExercicioEmbeddable(
    @Column(name = "exercicio_id", nullable = false)
    val exercicioId: Long,

    @Column(name = "series", nullable = false)
    val series: Int,

    @Column(name = "repeticoes", nullable = false)
    val repeticoes: Int,

    @Column(name = "descanso_segundos", nullable = false)
    val descansoSegundos: Int
)

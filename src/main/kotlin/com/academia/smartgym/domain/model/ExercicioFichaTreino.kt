package com.academia.smartgym.domain.model

data class ExercicioFichaTreino(
    val exercicioId: Long,
    val series: Int,
    val repeticoes: Int,
    val descansoSegundos: Int
)

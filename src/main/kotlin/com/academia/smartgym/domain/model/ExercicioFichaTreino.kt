package com.academia.smartgym.domain.model

data class ExercicioFichaTreino(
    val id: Int? = null,
    val exercicioId: Int,
    val series: Int,
    val repeticoes: Int,
    val descansoSegundos: Int
)

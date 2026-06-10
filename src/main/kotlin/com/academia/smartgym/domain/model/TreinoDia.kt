package com.academia.smartgym.domain.model

data class TreinoDia(
    val id: Int? = null,
    val letra: String,
    val focoTreino: String,
    val exercicios: List<ExercicioFichaTreino> = emptyList()
)
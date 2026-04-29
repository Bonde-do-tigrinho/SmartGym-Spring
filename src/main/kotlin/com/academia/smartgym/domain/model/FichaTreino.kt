package com.academia.smartgym.domain.model

import java.util.Date

data class FichaTreino(
    val id: Long? = null,
    val alunoId: Int,
    val exercicios: List<ExercicioFichaTreino>,
    val vigencia : Date,
    val focoTreino : String // Peito, costas ,etc
)

package com.academia.smartgym.domain.model

import java.time.LocalDate

data class Avaliacao(
    val id: Long? = null,
    val alunoId: Int,
    val nomeAluno: String,
    val dataAvaliacao: LocalDate,
    val peso: Double,
    val percentualGordura: Double,
    val imc: Double,
    val nota: String
)


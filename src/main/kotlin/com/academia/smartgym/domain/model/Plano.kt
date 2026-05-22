package com.academia.smartgym.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Plano(
    val id: Int? = null,
    val nome: String,
    val descricao: String,
    val valor: Double,
    val duracaoMeses: Int,
    val ativo: Boolean
)
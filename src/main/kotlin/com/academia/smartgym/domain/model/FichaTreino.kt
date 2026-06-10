package com.academia.smartgym.domain.model

import java.util.Date

data class FichaTreino(
    val id: Int? = null,
    val alunoId: Int?,
    val professorId: Int? = null,
    val vigencia: Date,
    val rotinaDias: List<TreinoDia> = emptyList()
) {
}



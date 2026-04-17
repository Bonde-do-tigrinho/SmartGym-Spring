package com.academia.smartgym.domain.model

enum class TipoExercicio { LIVRE, MAQUINA, AEROBICO }

    data class Exercicio(
    val id: Long? = null,
    val nome: String,
    val descricao: String,
    val tipo: TipoExercicio,
    val grupoMuscular: String?,
    val maquinaId: Long? = null
)
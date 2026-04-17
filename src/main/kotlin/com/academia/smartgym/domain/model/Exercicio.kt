package com.academia.smartgym.domain.model

enum class TipoExercicio { LIVRE, MAQUINA }

    data class Exercicio(
    val id: Long? = null,
    val nome: String,
    val descricao: String,
    val tipo: TipoExercicio = TipoExercicio.LIVRE,
    val maquinaId: Long? = null // ID da máquina se tipo for MAQUINA
)
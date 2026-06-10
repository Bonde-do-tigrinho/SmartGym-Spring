package com.academia.smartgym.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

enum class TipoExercicio { LIVRE, MAQUINA, AEROBICO }

data class Exercicio(
    val id: Int? = null,

    @field:NotBlank(message = "Nome é obrigatório!")
    @field:Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    val nome: String,

    @field:NotBlank(message = "Descrição é obrigatória!")
    @field:Size(min = 3, message = "Descrição deve ter no mínimo 3 caracteres")
    val descricao: String,

    @field:NotNull(message = "Tipo é obrigatório!")  // ✅ enum usa @NotNull
    val tipo: TipoExercicio,

    @field:Size(min = 2, message = "Grupo muscular deve ter no mínimo 2 caracteres")
    val grupoMuscular: String? = null,  // ✅ nullable não usa @NotBlank

    val maquinaId: Int? = null
)
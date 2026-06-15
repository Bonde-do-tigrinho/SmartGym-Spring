package com.academia.smartgym.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class Unidade(
    val id: Long? = null,

    @field:NotBlank(message = "Nome é obrigatório!")
    @field:Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    val nome: String,

    @field:NotBlank(message = "Endereço é obrigatório!")
    @field:Size(min = 3, message = "Endereço deve ter no mínimo 3 caracteres")
    val endereco: String,

    @field:NotBlank(message = "Cidade é obrigatório!")
    @field:Size(min = 3, message = "Cidade deve ter no mínimo 3 caracteres")
    val cidade: String,

    @field:NotNull(message = "O status da unidade é obrigatório")
    val ativa: Boolean = true
)
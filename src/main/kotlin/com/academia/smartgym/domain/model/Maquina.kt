package com.academia.smartgym.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

enum class StatusMaquina { LIVRE, OCUPADA, MANUTENCAO }

data class Maquina(
    val id: Long? = null,

    @field:NotBlank(message = "Nome é obrigatório!")
    @field:Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    val nome: String,

    @field:NotBlank(message = "Localização é obrigatória!")
    @field:Size(min = 3, message = "Localização deve ter no mínimo 3 caracteres")
    val localizacao: String,

    @field:NotNull(message = "Status é obrigatório!")
    val status: StatusMaquina = StatusMaquina.LIVRE
)
package com.academia.smartgym.domain.model

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class Plano(
    val id: Int? = null,

    @field:NotBlank(message = "Nome é obrigatório")
    @field:Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    val nome: String,

    @field:NotBlank(message = "Descrição é obrigatória")
    @field:Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    val descricao: String,

    @field:NotNull(message = "Valor é obrigatório")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "Valor deve ser maior que zero")
    val valor: Double,

    @field:NotNull(message = "Duração é obrigatória")
    @field:Min(value = 1, message = "Duração deve ser de no mínimo 1 mês")
    val duracaoMeses: Int,

    @field:NotNull(message = "Status é obrigatório")
    val ativo: Boolean
)
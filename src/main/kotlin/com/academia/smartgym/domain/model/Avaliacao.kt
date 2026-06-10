package com.academia.smartgym.domain.model

import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class Avaliacao(
    val id: Long? = null,

    @field:NotNull(message = "E necessario um aluno para poder fazer avaliacao!")
    val alunoId: Int,

    @field:NotBlank(message = "Nome do aluno é obrigatório!")
    @field:Size(min = 3, message = "O nome do aluno deve ter mais de 3 letras")
    val nomeAluno: String,

    @field:NotBlank(message = "E preciso de um professor para fazer avaliacao!")
    val professorId: Int?,

    @field:NotNull(message = "Data da avaliação é obrigatória!")
    val dataAvaliacao: LocalDate,

    @field:NotNull(message = "Peso é obrigatório!")
    @field:Positive(message = "Peso deve ser maior que zero")
    val peso: Double,

    @field:NotNull(message = "Percentual de gordura é obrigatório!")
    @field:DecimalMin(value = "0.0", message = "Percentual de gordura não pode ser negativo")
    @field:DecimalMax(value = "100.0", message = "Percentual de gordura não pode ser maior que 100")
    val percentualGordura: Double,

    @field:NotNull(message = "IMC é obrigatório!")
    @field:Positive(message = "IMC deve ser maior que zero")
    val imc: Double,

    @field:NotBlank(message = "Nota é obrigatória!")
    @field:Size(max = 500, message = "Nota deve ter no máximo 500 caracteres")
    val nota: String
)


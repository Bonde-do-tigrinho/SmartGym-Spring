package com.academia.smartgym.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Plano(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    @field:NotBlank(message = "Nome é obrigatório!")
    @field:Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    var nome: String,

    @field:NotNull(message = "É necessário definir se o plano está ativo ou não!")
    @Column(nullable = false)
    var ativo: Boolean,

    @field:NotNull(message = "Data fim da promoção é obrigatória!")
    @Column(nullable = false)
    var dataFimPromocao: LocalDate,

    @field:NotNull(message = "Horário limite de acesso é obrigatório!")
    @Column(nullable = false)
    var horarioLimiteAcesso: LocalTime
)
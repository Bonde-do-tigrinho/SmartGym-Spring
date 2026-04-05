package com.academia.smartgym.domain.model

enum class StatusMaquina { LIVRE, OCUPADA, MANUTENCAO }

data class Maquina(
    val id: Long? = null,
    val nome: String,
    val localizacao: String,
    val status: StatusMaquina = StatusMaquina.LIVRE
)
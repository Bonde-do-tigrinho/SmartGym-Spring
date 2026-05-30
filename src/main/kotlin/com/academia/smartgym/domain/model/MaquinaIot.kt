package com.academia.smartgym.domain.model

import java.io.Serializable

data class MaquinaIot(
    val id: String? = null,
    val nome: String,
    val localizacao: String,
    val status: StatusMaquinaIot,
    val deviceId: String? = null,
) : Serializable
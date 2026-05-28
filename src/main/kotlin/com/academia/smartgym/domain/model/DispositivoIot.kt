package com.academia.smartgym.domain.model

import java.io.Serializable

data class DispositivoIot(
    val id: String,
    val nome: String,
    val descricao: String? = null,
    val ativo: Boolean = true,
) : Serializable


package com.academia.smartgym.domain.model

import java.io.Serializable
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column

data class MaquinaIot(
    @JsonProperty("id")
    val id: String? = null,

    @JsonProperty("nome")
    val nome: String,

    @JsonProperty("localizacao")
    val localizacao: String,

    @JsonProperty("status")
    val status: StatusMaquinaIot,

    @Column(nullable = false)
    var categoria: String = "Cardio",

    @JsonProperty("deviceId")
    val deviceId: String? = null,
) : Serializable
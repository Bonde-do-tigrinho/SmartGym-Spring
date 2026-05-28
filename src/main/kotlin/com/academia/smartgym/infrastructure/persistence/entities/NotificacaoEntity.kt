package com.academia.smartgym.infrastructure.persistence.entities

import com.academia.smartgym.domain.model.CategoriaNotificacao
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notificacoes")
data class NotificacaoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false, length = 100)
    val titulo: String,

    @Column(nullable = false, length = 1000)
    val mensagem: String,

    @Column(nullable = false, name = "data_postagem")
    val dataPostagem: LocalDateTime,

    @Column(name = "data_expiracao")
    val dataExpiracao: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val categoria: CategoriaNotificacao
)
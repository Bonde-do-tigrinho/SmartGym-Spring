package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Notificacao

interface NotificacaoRepository {
    fun findAll(): List<Notificacao>
    fun findById(id: Int): Notificacao?
    fun save(notificacao: Notificacao): Notificacao
    fun deleteById(id: Int)
}
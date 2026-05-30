package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Notificacao

interface NotificacaoRepository {
    fun salvar(notificacao: Notificacao): Notificacao
    fun listarTodas(): List<Notificacao>
    fun buscarPorId(id: Int): Notificacao?
    fun deletar(id: Int)
}
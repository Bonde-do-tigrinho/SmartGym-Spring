package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Notificacao
import com.academia.smartgym.domain.repository.NotificacaoRepository
import org.springframework.stereotype.Service

@Service
class NotificacaoUseCase(
    private val notificacaoRepository: NotificacaoRepository
) {
    fun criar(notificacao: Notificacao): Notificacao {
        return notificacaoRepository.salvar(notificacao)
    }

    fun atualizar(id: Int, notificacaoAtualizada: Notificacao): Notificacao {
        val existente = buscarPorId(id)
        val novaNotificacao = notificacaoAtualizada.copy(id = existente.id)
        return notificacaoRepository.salvar(novaNotificacao)
    }

    fun listarTodas(): List<Notificacao> {
        return notificacaoRepository.listarTodas().sortedByDescending { it.dataPostagem }
    }

    fun buscarPorId(id: Int): Notificacao {
        return notificacaoRepository.buscarPorId(id)
            ?: throw IllegalArgumentException("Notificação com ID $id não encontrada.")
    }

    fun deletar(id: Int) {
        val existente = buscarPorId(id)
        notificacaoRepository.deletar(existente.id!!)
    }
}
package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.Notificacao
import com.academia.smartgym.domain.repository.NotificacaoRepository
import com.academia.smartgym.infrastructure.persistence.entities.NotificacaoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository


@Repository
interface SpringNotificacaoRepository : JpaRepository<NotificacaoEntity, Int>


@Component
class NotificacaoRepositoryImpl(
    private val springRepository: SpringNotificacaoRepository
) : NotificacaoRepository {

    override fun salvar(notificacao: Notificacao): Notificacao {
        val entity = toEntity(notificacao)
        val salva = springRepository.save(entity)
        return toDomain(salva)
    }

    override fun listarTodas(): List<Notificacao> {
        return springRepository.findAll().map { toDomain(it) }
    }

    override fun buscarPorId(id: Int): Notificacao? {
        return springRepository.findById(id).orElse(null)?.let { toDomain(it) }
    }

    override fun deletar(id: Int) {
        springRepository.deleteById(id)
    }


    private fun toEntity(domain: Notificacao) = NotificacaoEntity(
        id = domain.id,
        titulo = domain.titulo,
        mensagem = domain.mensagem,
        dataPostagem = domain.dataPostagem,
        dataExpiracao = domain.dataExpiracao,
        categoria = domain.categoria
    )

    private fun toDomain(entity: NotificacaoEntity) = Notificacao(
        id = entity.id,
        titulo = entity.titulo,
        mensagem = entity.mensagem,
        dataPostagem = entity.dataPostagem,
        dataExpiracao = entity.dataExpiracao,
        categoria = entity.categoria
    )
}
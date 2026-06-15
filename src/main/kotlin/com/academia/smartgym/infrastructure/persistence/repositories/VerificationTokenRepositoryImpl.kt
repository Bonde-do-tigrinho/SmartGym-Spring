package com.academia.smartgym.infrastructure.persistence

import com.academia.smartgym.domain.model.VerificationToken
import com.academia.smartgym.domain.repository.VerificationTokenRepository
import com.academia.smartgym.infrastructure.persistence.entities.VerificationTokenEntity
import com.academia.smartgym.infrastructure.persistence.repositories.SpringVerificationTokenRepository
import org.springframework.stereotype.Repository
import jakarta.transaction.Transactional

@Repository
class VerificationTokenRepositoryImpl(
    private val jpa: SpringVerificationTokenRepository
) : VerificationTokenRepository {

    override fun salvar(token: VerificationToken): VerificationToken {
        val entity = VerificationTokenEntity(
            id = token.id,
            token = token.token,
            usuarioId = token.usuarioId,
            expiracao = token.expiracao
        )
        val saved = jpa.save(entity)
        return token.copy(id = saved.id)
    }

    override fun buscarPorToken(token: String): VerificationToken? {
        return jpa.findByToken(token)?.let {
            VerificationToken(
                id = it.id,
                token = it.token,
                usuarioId = it.usuarioId,
                expiracao = it.expiracao
            )
        }
    }

    @Transactional
    override fun deletarPorUsuarioId(usuarioId: Int) {
        jpa.deleteByUsuarioId(usuarioId)
    }
}
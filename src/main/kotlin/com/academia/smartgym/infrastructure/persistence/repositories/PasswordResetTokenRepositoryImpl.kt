package com.academia.smartgym.infrastructure.persistence

import com.academia.smartgym.domain.model.PasswordResetToken
import com.academia.smartgym.domain.repository.PasswordResetTokenRepository
import com.academia.smartgym.infrastructure.persistence.entities.PasswordResetTokenEntity
import com.academia.smartgym.infrastructure.persistence.repositories.SpringPasswordResetTokenRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class PasswordResetTokenRepositoryImpl(
    private val jpa: SpringPasswordResetTokenRepository
) : PasswordResetTokenRepository {

    override fun salvar(token: PasswordResetToken): PasswordResetToken {
        val entity = PasswordResetTokenEntity(
            id = token.id,
            token = token.token,
            usuarioId = token.usuarioId,
            expiracao = token.expiracao
        )
        val saved = jpa.save(entity)
        return token.copy(id = saved.id)
    }

    override fun buscarPorToken(token: String): PasswordResetToken? {
        return jpa.findByToken(token)?.let {
            PasswordResetToken(
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
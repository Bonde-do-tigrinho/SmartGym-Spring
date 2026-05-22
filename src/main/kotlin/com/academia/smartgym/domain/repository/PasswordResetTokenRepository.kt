package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.PasswordResetToken

interface PasswordResetTokenRepository {
    fun salvar(token: PasswordResetToken): PasswordResetToken
    fun buscarPorToken(token: String): PasswordResetToken?
    fun deletarPorUsuarioId(usuarioId: Int)
}
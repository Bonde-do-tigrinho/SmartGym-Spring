package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.VerificationToken

interface VerificationTokenRepository {
    fun salvar(token: VerificationToken): VerificationToken
    fun buscarPorToken(token: String): VerificationToken?
    fun deletarPorUsuarioId(usuarioId: Int)
}
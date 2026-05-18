package com.academia.smartgym.infrastructure.api.security.services

import com.academia.smartgym.domain.repository.UsuarioRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val usuarioRepository: UsuarioRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val usuario = usuarioRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("Usuário não encontrado: $username")

        return User.builder()
            .username(usuario.email)
            .password(usuario.senha ?: "")
            .authorities(SimpleGrantedAuthority("ROLE_${usuario.role.name ?: "ALUNO"}"))
            .build()
    }
}
package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.domain.model.DashboardResponse
import com.academia.smartgym.domain.model.UserRole
import com.academia.smartgym.domain.repository.UnidadeRepository
import com.academia.smartgym.domain.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin/dashboard")
class DashboardController(
    private val usuarioRepository: UsuarioRepository,
    private val unidadeRepository: UnidadeRepository
) {

    @GetMapping
    fun getDashboard(): ResponseEntity<DashboardResponse> {
        val alunos = usuarioRepository.findByRole(UserRole.ALUNO)
        val professores = usuarioRepository.findByRole(UserRole.PROFESSOR)
        val unidades = unidadeRepository.findAll()

        return ResponseEntity.ok(
            DashboardResponse(
                totalAlunos = alunos.size,
                totalProfessores = professores.size,
                totalUnidades = unidades.size,
                alunosAtivos = alunos.count { it.status == true },
                alunosInativos = alunos.count { it.status == false }
            )
        )
    }
}
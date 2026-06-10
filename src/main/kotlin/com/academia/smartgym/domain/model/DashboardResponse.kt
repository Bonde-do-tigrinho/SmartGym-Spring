package com.academia.smartgym.domain.model

data class DashboardResponse(
    val totalAlunos: Int,
    val totalProfessores: Int,
    val totalUnidades: Int,
    val alunosAtivos: Int,
    val alunosInativos: Int
)
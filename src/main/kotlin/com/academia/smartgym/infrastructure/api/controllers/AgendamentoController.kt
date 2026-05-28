package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.AgendamentoUseCase
import com.academia.smartgym.domain.model.Agendamento
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/agendamentos")
class AgendamentoController(private val useCase: AgendamentoUseCase) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun agendar(@Valid @RequestBody agendamento: Agendamento) =
        useCase.realizarAgendamento(agendamento)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun cancelarAgendamento(@PathVariable id: Long) {
        useCase.cancelarAgendamento(id)
    }

    @GetMapping("/aluno/{alunoId}")
    fun listarPorAluno(@PathVariable alunoId: Long): List<Agendamento> =
        useCase.listarAgendamentosDoAluno(alunoId)

    @GetMapping("/aula/{aulaId}")
    fun listarPorAula(@PathVariable aulaId: Long): List<Agendamento> =
        useCase.listarAgendamentosDaAula(aulaId)
}
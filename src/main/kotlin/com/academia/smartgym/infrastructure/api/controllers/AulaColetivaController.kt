package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.AulaColetivaUseCase
import com.academia.smartgym.domain.model.AulaColetiva
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/aulas-coletivas")
class AulaColetivaController(private val useCase: AulaColetivaUseCase) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody aula: AulaColetiva) =
        useCase.criarAula(aula)

    // O endpoint feito sob medida para o seu front-end KMP!
    @GetMapping("/semana")
    fun getAulasDaSemana(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dataInicio: LocalDate
    ) = useCase.listarAulasDaSemana(dataInicio)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): AulaColetiva =
        useCase.buscarPorId(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody aulaAtualizada: AulaColetiva) =
        useCase.atualizarAula(id, aulaAtualizada)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna 204 (Sem conteúdo) que é o padrão para DELETE com sucesso
    fun delete(@PathVariable id: Long) {
        useCase.excluirAula(id)
    }
}
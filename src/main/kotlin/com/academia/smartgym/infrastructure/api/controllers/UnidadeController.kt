package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.UnidadeUseCase
import com.academia.smartgym.domain.model.Unidade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/unidades")
class UnidadeController(private val useCase: UnidadeUseCase) {

    @GetMapping
    fun listarTodas(): ResponseEntity<List<Unidade>> {
        return ResponseEntity.ok(useCase.listarTodas())
    }

    @PostMapping
    fun criar(@RequestBody unidade: Unidade): ResponseEntity<Unidade> {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.salvar(unidade))
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody unidade: Unidade): ResponseEntity<Unidade> {
        return ResponseEntity.ok(useCase.atualizar(id, unidade))
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
        useCase.deletar(id)
        return ResponseEntity.noContent().build()
    }
}
package com.academia.smartgym.infrastructure.api

import com.academia.smartgym.domain.model.Plano
import com.academia.smartgym.application.usecases.PlanoUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/planos")
@CrossOrigin(origins = ["*"])
class PlanoController(private val planoUseCase: PlanoUseCase) {

    @PostMapping
    fun criar(@RequestBody plano: Plano): ResponseEntity<Plano> {
        val novo = planoUseCase.salvar(plano)
        return ResponseEntity(novo, HttpStatus.CREATED)
    }

    @GetMapping
    fun listar(): ResponseEntity<List<Plano>> {
        return ResponseEntity.ok(planoUseCase.listarTodos())
    }

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Long): ResponseEntity<Plano> {
        val plano = planoUseCase.buscarPorId(id)
        return if (plano != null) ResponseEntity.ok(plano) else ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody plano: Plano): ResponseEntity<Plano> {
        return try {
            ResponseEntity.ok(planoUseCase.atualizar(id, plano))
        } catch (e: RuntimeException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
        planoUseCase.deletar(id)
        return ResponseEntity.noContent().build()
    }
}
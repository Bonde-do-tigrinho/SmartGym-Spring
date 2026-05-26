package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.PlanoUseCase
import com.academia.smartgym.domain.model.Plano
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/planos")
class PlanoController(private val planoUseCase: PlanoUseCase) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPlano(@Valid @RequestBody plano: Plano): Plano {
        return planoUseCase.create(plano)
    }

    @GetMapping
    fun getAllPlanos(): List<Plano> {
        return planoUseCase.findAll()
    }

    @GetMapping("/{id}")
    fun getPlanoById(@PathVariable id: Int): ResponseEntity<Plano> {
        val plano = planoUseCase.findById(id)
        return if (plano != null) {
            ResponseEntity.ok(plano)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun updatePlano(@PathVariable id: Int, @Valid @RequestBody plano: Plano): ResponseEntity<Plano> {
        return try {
            val updatedPlano = planoUseCase.update(id, plano)
            ResponseEntity.ok(updatedPlano)
        } catch (e: RuntimeException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePlano(@PathVariable id: Int) {
        planoUseCase.delete(id)
    }
}
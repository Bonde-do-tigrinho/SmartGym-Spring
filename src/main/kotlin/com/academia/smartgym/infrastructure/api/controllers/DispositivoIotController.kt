package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.DispositivoIotUseCase
import com.academia.smartgym.domain.model.DispositivoIot
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/dispositivos-iot")
class DispositivoIotController(
    private val dispositivoIotUseCase: DispositivoIotUseCase
) {

    @GetMapping
    fun findAll(): List<DispositivoIot> {
        // Força o retorno estrito de uma lista de domínios DispositivoIot
        return dispositivoIotUseCase.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<DispositivoIot> {
        val dispositivo = dispositivoIotUseCase.findById(id)
        return if (dispositivo != null) {
            ResponseEntity.ok(dispositivo)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody dispositivoIot: DispositivoIot): DispositivoIot {
        return dispositivoIotUseCase.create(dispositivoIot)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody dispositivoIot: DispositivoIot
    ): ResponseEntity<DispositivoIot> {
        return try {
            val updated = dispositivoIotUseCase.update(id, dispositivoIot)
            ResponseEntity.ok(updated)
        } catch (_: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) {
        dispositivoIotUseCase.delete(id)
    }
}
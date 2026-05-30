package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.DispositivoIotUseCase
import com.academia.smartgym.domain.model.DispositivoIot
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dispositivos-iot")
class DispositivoIotController(
    private val dispositivoIotUseCase: DispositivoIotUseCase
) {

    @GetMapping
    fun findAll(): List<DispositivoIot> = dispositivoIotUseCase.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<DispositivoIot> {
        val dispositivo = dispositivoIotUseCase.findById(id)
        return if (dispositivo != null) ResponseEntity.ok(dispositivo) else ResponseEntity.notFound().build()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody dispositivoIot: DispositivoIot): DispositivoIot =
        dispositivoIotUseCase.create(dispositivoIot)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody dispositivoIot: DispositivoIot): ResponseEntity<DispositivoIot> {
        return try {
            ResponseEntity.ok(dispositivoIotUseCase.update(id, dispositivoIot))
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



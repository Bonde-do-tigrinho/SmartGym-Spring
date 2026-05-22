package com.academia.smartgym.infrastructure.api.controller

import com.academia.smartgym.application.usecases.MaquinaIotUseCase
import com.academia.smartgym.domain.model.MaquinaIot
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/maquinas-iot")
class MaquinaIotController(
    private val maquinaIotUseCase: MaquinaIotUseCase
) {

    @GetMapping
    fun getAllMaquinasIot(@RequestParam(required = false) nome: String?): List<MaquinaIot> {
        return maquinaIotUseCase.findAll(nome)
    }

    @GetMapping("/{id}")
    fun getMaquinaIotById(@PathVariable id: String): ResponseEntity<MaquinaIot> {
        val maquinaIot = maquinaIotUseCase.findById(id)
        return if (maquinaIot != null) {
            ResponseEntity.ok(maquinaIot)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMaquinaIot(@RequestBody maquinaIot: MaquinaIot): MaquinaIot {
        return maquinaIotUseCase.create(maquinaIot)
    }

    @PutMapping("/{id}")
    fun updateMaquinaIot(@PathVariable id: String, @RequestBody maquinaIot: MaquinaIot): ResponseEntity<MaquinaIot> {
        return try {
            val updatedMaquinaIot = maquinaIotUseCase.update(id, maquinaIot)
            ResponseEntity.ok(updatedMaquinaIot)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMaquinaIot(@PathVariable id: String) {
        maquinaIotUseCase.delete(id)
    }
}
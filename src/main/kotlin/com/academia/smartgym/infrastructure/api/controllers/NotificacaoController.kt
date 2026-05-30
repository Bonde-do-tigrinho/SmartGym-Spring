package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.NotificacaoUseCase
import com.academia.smartgym.domain.model.Notificacao
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notificacoes")
class NotificacaoController(
    private val notificacaoUseCase: NotificacaoUseCase
) {

    @PostMapping
    fun criar(@RequestBody notificacao: Notificacao): ResponseEntity<Notificacao> {
        val nova = notificacaoUseCase.criar(notificacao)
        return ResponseEntity.status(HttpStatus.CREATED).body(nova)
    }

    @GetMapping
    fun listarTodas(): ResponseEntity<List<Notificacao>> {
        val lista = notificacaoUseCase.listarTodas()
        return ResponseEntity.ok(lista)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Int): ResponseEntity<Notificacao> {
        val notificacao = notificacaoUseCase.buscarPorId(id)
        return ResponseEntity.ok(notificacao)
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Int,
        @RequestBody notificacao: Notificacao
    ): ResponseEntity<Notificacao> {
        val atualizada = notificacaoUseCase.atualizar(id, notificacao)
        return ResponseEntity.ok(atualizada)
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Int): ResponseEntity<Void> {
        notificacaoUseCase.deletar(id)
        return ResponseEntity.noContent().build()
    }
}
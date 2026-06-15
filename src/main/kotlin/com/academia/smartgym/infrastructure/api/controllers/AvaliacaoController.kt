package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.AvaliacaoUseCase
import com.academia.smartgym.application.usecases.UsuarioUseCase
import com.academia.smartgym.domain.model.Avaliacao
import com.academia.smartgym.domain.repository.AvaliacaoRepository
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/avaliacoes")
class AvaliacaoController(
    private val useCase: AvaliacaoUseCase,
    private val usuarioUseCase: UsuarioUseCase,
    private val avaliacaoRepository: AvaliacaoRepository
) {

    @GetMapping
    fun getAll(@RequestParam(required = false) nome: String?) =
        if (nome.isNullOrBlank())
            useCase.listarTodas()
        else
            useCase.buscarPorNomeAluno(nome)

    @GetMapping("/professor")
    fun listarAvaliacoesDoProfessor(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<List<Avaliacao>> {
        val professor = usuarioUseCase.buscarPorEmail(userDetails.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val avaliacoesProfessor = avaliacaoRepository.findByProfessorId(professor.id)

        return ResponseEntity.ok(avaliacoesProfessor)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = useCase.buscarPorId(id)

    @PostMapping
    fun create(
        @RequestBody avaliacao: Avaliacao,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Any> {
        val professor = usuarioUseCase.buscarPorEmail(userDetails.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val avaliacaoProntaParaSalvar = avaliacao.copy(
            professorId = professor.id
        )

        val avaliacaoSalva = useCase.salvar(avaliacaoProntaParaSalvar)

        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoSalva)
    }

    @PutMapping("/{id}")
    fun atualizarAvaliacao(
        @PathVariable id: Long,
        @RequestBody avaliacaoAlterada: Avaliacao,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Avaliacao> {
        val professor = usuarioUseCase.buscarPorEmail(userDetails.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val avaliacaoAntiga = avaliacaoRepository.findById(id)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        val avaliacaoParaSalvar = avaliacaoAlterada.copy(
            id = id,
            professorId = professor.id
        )

        val avaliacaoSalva = avaliacaoRepository.save(avaliacaoParaSalvar)
        return ResponseEntity.ok(avaliacaoSalva)
    }

    @DeleteMapping("/{id}")
    fun delete(@Valid @PathVariable id: Long) = useCase.excluir(id)

    @GetMapping("/aluno/{alunoId}")
    fun getByAluno(@Valid @PathVariable alunoId: Int) = useCase.listarPorAluno(alunoId)
}


package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.FichaTreinoUseCase
import com.academia.smartgym.application.usecases.UsuarioUseCase // 👈 Injetar o seu caso de uso de usuários
import com.academia.smartgym.domain.model.FichaTreino
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/fichas-treino")
class FichaTreinoController(
    private val useCase: FichaTreinoUseCase,
    private val usuarioUseCase: UsuarioUseCase
) {

    @GetMapping
    fun getFichas(
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<List<FichaTreino>> {
        val professor = usuarioUseCase.buscarPorEmail(userDetails.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val fichasDoProfessor = useCase.listarPorProfessor(professor.id)

        return ResponseEntity.ok(fichasDoProfessor)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int) = useCase.buscarPorId(id)

    @PostMapping
    fun create(
        @RequestBody fichaTreino: FichaTreino,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Any> {
        val professor = usuarioUseCase.buscarPorEmail(userDetails.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Professor não autenticado.")

        val aluno = usuarioUseCase.buscar(fichaTreino.alunoId)
            ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aluno não encontrado.")

        if (aluno.professorId != professor.id) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Erro: Você só pode criar fichas de treino para alunos vinculados ao seu perfil.")
        }

        val fichaSalva = useCase.salvar(fichaTreino.copy(professorId = professor.id))

        return ResponseEntity.status(HttpStatus.CREATED).body(fichaSalva)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody fichaTreino: FichaTreino,
        @AuthenticationPrincipal userDetails: UserDetails
    ): FichaTreino {
        val professor = usuarioUseCase.buscarPorEmail(userDetails.username)
            ?: throw RuntimeException("Apenas instrutores válidos podem alterar treinos")

        return useCase.salvar(fichaTreino.copy(id = id, professorId = professor.id))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) = useCase.excluir(id)

    @GetMapping("/me")
    fun getMinhaFichaAtiva(
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<FichaTreino> {
        val aluno = usuarioUseCase.buscarPorEmail(userDetails.username)
            ?: return ResponseEntity.status(404).build()

        val fichasDoAluno = useCase.listarPorAluno(aluno.id)

        val fichaAtiva = fichasDoAluno.maxByOrNull { it.id ?: 0 }
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(fichaAtiva)
    }
}
package com.academia.smartgym.infrastructure.config

import com.academia.smartgym.application.usecases.*
import com.academia.smartgym.domain.model.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@ConditionalOnProperty(prefix = "app.seed", name = ["enabled"], havingValue = "true")
class DevelopmentDataSeeder(
    private val usuarioUseCase: UsuarioUseCase,
    private val maquinaUseCase: MaquinaUseCase,
    private val exercicioUseCase: ExercicioUseCase,
    private val avaliacaoUseCase: AvaliacaoUseCase,
    private val unidadeUseCase: UnidadeUseCase // Adicionado para as Unidades
) : CommandLineRunner {

    override fun run(vararg args: String) {
        val alunos = seedAlunos()
        val maquinas = seedMaquinas()
        seedAdmin()

        seedExercicios(maquinas)
        seedAvaliacoes(alunos)
        seedUnidades() // Chamada para criar as unidades fixas
    }

    private fun seedAdmin() {
        val existentes = usuarioUseCase.listar()
        val adminExiste = existentes.any { it.role == UserRole.ADMIN }
        if (adminExiste) return

        usuarioUseCase.criarSemEmail(
            Usuario(
                id = null,
                nome = "Admin SmartGym",
                email = "admin@smartgym.com",
                cpf = "00000000000",
                telefone = "11999999999",
                role = UserRole.ADMIN,
                senha = "admin123",
                treinoAtual = null,
                focoTreino = null,
                planoVencimento = null,
                planoValor = null
            )
        )
    }
    private fun seedUnidades() {
        // Só insere se o banco de unidades estiver vazio
        if (unidadeUseCase.listarTodas().isEmpty()) {
            val base = listOf(
                Unidade(id = null, nome = "Unidade Centro", endereco = "Rua Principal, 123", cidade = "São Paulo - SP"),
                Unidade(id = null, nome = "Unidade Zona Sul", endereco = "Av. Paulista, 456", cidade = "São Paulo - SP"),
                Unidade(id = null, nome = "Unidade Zona Oeste", endereco = "Rua Secundária, 789", cidade = "São Paulo - SP")
            )
            base.forEach { unidadeUseCase.salvar(it) }
            println(" Unidades fixas inseridas no banco")
        }
    }

    private fun seedAlunos(): List<Usuario> {
        val existentes = usuarioUseCase.listar()
        if (existentes.isNotEmpty()) return existentes

        val base = listOf(
            Usuario(id = null, nome = "Lucas Mendes", email = "lucas.mendes@smartgym.com", cpf = "11111111111", telefone = "11990000001", plano = "Mensal", status = true, treinoAtual = "Hipertrofia A", focoTreino = "Ganho de massa", planoVencimento = "2026-05-15", planoValor = 149.90, role = UserRole.ALUNO),
            Usuario(id = null, nome = "Fernanda Lima", email = "fernanda.lima@smartgym.com", cpf = "22222222222", telefone = "11990000002", plano = "Trimestral", status = true, treinoAtual = "Forca B", focoTreino = "Condicionamento", planoVencimento = "2026-07-10", planoValor = 399.90, role = UserRole.ALUNO),
            Usuario(id = null, nome = "Lucas Penteado", email = "lulu123@smartgym.com", cpf = "22222211112", telefone = "11990000002", plano = "Trimestral", status = true, treinoAtual = "Forca B", focoTreino = "Condicionamento", planoVencimento = "2026-07-10", planoValor = 399.90, role = UserRole.PROFESSOR)
        )

        base.forEach { usuarioUseCase.criarSemEmail(it) }
        return usuarioUseCase.listar()
    }

    private fun seedMaquinas(): List<Maquina> {
        val existentes = maquinaUseCase.listarTodas()
        if (existentes.isNotEmpty()) return existentes

        val base = listOf(
            Maquina(nome = "Leg Press 45", localizacao = "Sala 1", status = StatusMaquina.LIVRE),
            Maquina(nome = "Supino Reto", localizacao = "Sala 2", status = StatusMaquina.OCUPADA),
            Maquina(nome = "Puxador Frontal", localizacao = "Sala 3", status = StatusMaquina.MANUTENCAO)
        )

        base.forEach { maquinaUseCase.salvar(it) }
        return maquinaUseCase.listarTodas()
    }

    private fun seedExercicios(maquinas: List<Maquina>) {
        if (exercicioUseCase.listarTodos().isNotEmpty()) return
        val primeiraMaquinaId = maquinas.firstOrNull()?.id
        val segundaMaquinaId = maquinas.drop(1).firstOrNull()?.id ?: primeiraMaquinaId

        val base = mutableListOf(
            Exercicio(nome = "Agachamento Livre", descricao = "Descrição do exercicio", tipo = TipoExercicio.LIVRE, grupoMuscular = "quadriceps", maquinaId = null),
            Exercicio(nome = "Prancha", descricao = "Descrição do exercicio", tipo = TipoExercicio.LIVRE, grupoMuscular = "Abdomen", maquinaId = null)
        )

        if (primeiraMaquinaId != null) {
            base.add(Exercicio(nome = "Leg Press", descricao = "4 series de 10 repeticoes", tipo = TipoExercicio.MAQUINA, maquinaId = primeiraMaquinaId, grupoMuscular = "Quadriceps"))
        }

        if (segundaMaquinaId != null) {
            base.add(Exercicio(nome = "Puxada Alta", descricao = "4 series de 12 repeticoes", tipo = TipoExercicio.MAQUINA, maquinaId = segundaMaquinaId, grupoMuscular = "Costas"))
        }

        base.forEach { exercicioUseCase.salvar(it) }
    }

    private fun seedAvaliacoes(usuarios: List<Usuario>) {
        if (avaliacaoUseCase.listarTodas().isNotEmpty()) return
        val base = usuarios.take(2).mapIndexedNotNull { index, aluno ->
            val alunoId = aluno.id ?: return@mapIndexedNotNull null
            Avaliacao(alunoId = alunoId, nomeAluno = aluno.nome, dataAvaliacao = LocalDate.now().minusDays(index.toLong()), peso = if (index == 0) 78.5 else 62.0, percentualGordura = if (index == 0) 15.2 else 22.5, imc = if (index == 0) 25.6 else 22.8, nota = if (index == 0) "Boa evolucao. Manter treino atual." else "Iniciar treino de forca.")
        }
        base.forEach { avaliacaoUseCase.salvar(it) }
    }
}
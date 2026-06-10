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
    private val dispositivoIotUseCase: DispositivoIotUseCase,
    private val maquinaIotUseCase: MaquinaIotUseCase,
    private val exercicioUseCase: ExercicioUseCase,
    private val avaliacaoUseCase: AvaliacaoUseCase,
    private val unidadeUseCase: UnidadeUseCase,
    private val planoUseCase: PlanoUseCase
) : CommandLineRunner {

    override fun run(vararg args: String) {
        val alunos = seedAlunos()
        val dispositivos = seedDispositivosIot()
        seedMaquinasIot(dispositivos)
        seedAdmin()
//        seedExercicios(maquinas)
        seedAvaliacoes(alunos)
        seedUnidades()
        seedPlanos()
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
                planoVencimento = null,
                emailVerificado = true
            )
        )
    }
    private fun seedUnidades() {
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
            Usuario(id = null, nome = "Lucas Mendes", email = "lucas.mendes@smartgym.com", cpf = "11111111111", telefone = "11990000001", status = true, planoVencimento = "2026-05-15", role = UserRole.ALUNO, emailVerificado = true),
            Usuario(id = null, nome = "Fernanda Lima", email = "fernanda.lima@smartgym.com", cpf = "22222222222", telefone = "11990000002", status = true, planoVencimento = "2026-07-10", role = UserRole.ALUNO, emailVerificado = true),
            Usuario(id = null, nome = "Lucas Penteado", email = "lulu123@smartgym.com", cpf = "22222211112", telefone = "11990000002", status = true, planoVencimento = "2026-07-10", role = UserRole.PROFESSOR, emailVerificado = true, senha = "prof12345")
        )

        base.forEach { usuarioUseCase.criarSemEmail(it) }
        return usuarioUseCase.listar()
    }

    private fun seedDispositivosIot(): List<DispositivoIot> {
        val existentes = dispositivoIotUseCase.findAll()
        if (existentes.isNotEmpty()) return existentes

        val base = listOf(
            DispositivoIot(id = "tcrt5000", nome = "Sensor TCRT5000 - Leg Press", descricao = "Sensor da esteira/leg press principal", ativo = true),
            DispositivoIot(id = "esp32_supino_01", nome = "ESP32 Supino 01", descricao = "Sensor do supino reto", ativo = true),
            DispositivoIot(id = "esp32_puxador_01", nome = "ESP32 Puxador 01", descricao = "Sensor do puxador frontal", ativo = true)
        )

        base.forEach { dispositivoIotUseCase.create(it) }
        return dispositivoIotUseCase.findAll()
    }

    private fun seedMaquinasIot(dispositivos: List<DispositivoIot>) {
        if (maquinaIotUseCase.findAll(null).isNotEmpty()) return

        val deviceLegPress = dispositivos.firstOrNull { it.id == "tcrt5000" }?.id ?: return
        val deviceSupino = dispositivos.firstOrNull { it.id == "esp32_supino_01" }?.id ?: deviceLegPress
        val devicePuxador = dispositivos.firstOrNull { it.id == "esp32_puxador_01" }?.id ?: deviceLegPress

        val base = listOf(
            MaquinaIot(id = null, nome = "Esteira IoT 1", localizacao = "Sala IoT A", status = StatusMaquinaIot.LIVRE, deviceId = deviceLegPress),
            MaquinaIot(id = null, nome = "Esteira IoT 2", localizacao = "Sala IoT B", status = StatusMaquinaIot.LIVRE, deviceId = deviceSupino),
            MaquinaIot(id = null, nome = "Esteira IoT 3", localizacao = "Sala IoT C", status = StatusMaquinaIot.MANUTENCAO, deviceId = devicePuxador)
        )

        base.forEach { maquinaIotUseCase.create(it) }
        println(" Dispositivos IoT e máquinas IoT fixas inseridos no banco")
    }

//    private fun seedExercicios(maquinas: List<Maquina>) {
//        if (exercicioUseCase.listarTodos().isNotEmpty()) return
//
//        val primeiraMaquinaId = maquinas.firstOrNull()?.id
//        val segundaMaquinaId = maquinas.drop(1).firstOrNull()?.id ?: primeiraMaquinaId
//
//        val base = mutableListOf(
//            Exercicio(id = null, nome = "Agachamento Livre", descricao = "Descrição do exercicio", tipo = TipoExercicio.LIVRE, grupoMuscular = "quadriceps", maquinaId = null),
//            Exercicio(id = null, nome = "Prancha", descricao = "Descrição do exercicio", tipo = TipoExercicio.LIVRE, grupoMuscular = "Abdomen", maquinaId = null)
//        )
//
//        if (primeiraMaquinaId != null) {
//            base.add(Exercicio(id = null, nome = "Leg Press", descricao = "4 series de 10 repeticoes", tipo = TipoExercicio.MAQUINA, maquinaId = 1, grupoMuscular = "Quadriceps"))
//        }
//
//        if (segundaMaquinaId != null) {
//            base.add(Exercicio(id = null, nome = "Puxada Alta", descricao = "4 series de 12 repeticoes", tipo = TipoExercicio.MAQUINA, maquinaId = 2, grupoMuscular = "Costas"))
//        }
//
//        base.forEach { exercicioUseCase.salvar(it) }
//        println(" Exercícios de teste associados às máquinas inseridos com sucesso!")
//    }

    private fun seedAvaliacoes(usuarios: List<Usuario>) {
        if (avaliacaoUseCase.listarTodas().isNotEmpty()) return
        val base = usuarios.take(2).mapIndexedNotNull { index, aluno ->
            val alunoId = aluno.id ?: return@mapIndexedNotNull null
            Avaliacao(alunoId = alunoId, nomeAluno = aluno.nome, dataAvaliacao = LocalDate.now().minusDays(index.toLong()), peso = if (index == 0) 78.5 else 62.0, percentualGordura = if (index == 0) 15.2 else 22.5, imc = if (index == 0) 25.6 else 22.8, professorId = 3, nota = if (index == 0) "Boa evolucao. Manter treino atual." else "Iniciar treino de forca.")
        }
        base.forEach { avaliacaoUseCase.salvar(it) }
    }

    private fun seedPlanos(){
        if (planoUseCase.findAll().isNotEmpty()) return

        val base = listOf(
            Plano(
                nome = "Mensal",
                descricao = "Acesso completo à academia por 1 mês",
                valor = 149.90,
                duracaoMeses = 1,
                ativo = true
            ),
            Plano(
                nome = "Trimestral",
                descricao = "Acesso completo à academia por 3 meses com desconto",
                valor = 399.90,
                duracaoMeses = 3,
                ativo = true
            ),
            Plano(
                nome = "Semestral",
                descricao = "Acesso completo à academia por 6 meses com desconto especial",
                valor = 699.90,
                duracaoMeses = 6,
                ativo = true
            ),
            Plano(
                nome = "Anual",
                descricao = "Acesso completo à academia por 12 meses com melhor custo-benefício",
                valor = 1199.90,
                duracaoMeses = 12,
                ativo = true
            )
        )

        base.forEach { planoUseCase.create(it) }
    }
}
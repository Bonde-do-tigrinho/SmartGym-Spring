package com.academia.smartgym.infrastructure.api.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter,
    private val userDetailsService: UserDetailsService
) {

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider(userDetailsService)
        provider.setPasswordEncoder(passwordEncoder())
        return provider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/auth/**").permitAll()

                    //AlunoController
                    .requestMatchers(HttpMethod.GET, "/api/alunos").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/alunos").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/alunos/{id}").hasAnyRole("ADMIN", "ALUNO")
                    .requestMatchers(HttpMethod.POST, "/api/alunos").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/alunos/{id}").hasAnyRole("ADMIN", "ALUNO")
                    .requestMatchers(HttpMethod.DELETE, "/api/alunos/{id}").hasRole("ADMIN")

                    //ProfessorController
                    .requestMatchers(HttpMethod.GET, "/api/professores").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/professores/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/professores").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/professores/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/professores/{id}").hasRole("ADMIN")

                    //AvaliacoesController
                    .requestMatchers(HttpMethod.GET, "/api/avaliacoes").hasRole( "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/avaliacoes/{id}").hasAnyRole( "PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.GET, "/api/avaliacoes/aluno/{alunoId}").hasAnyRole( "PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.POST, "/api/avaliacoes").hasRole( "PROFESSOR")
                    .requestMatchers(HttpMethod.PUT, "/api/avaliacoes/{id}").hasRole( "PROFESSOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/avaliacoes/{id}").hasRole( "PROFESSOR")

                    //ExerciciosController
                    .requestMatchers(HttpMethod.GET, "/api/exercicios").hasAnyRole("PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.GET, "/api/exercicios/{id}").hasAnyRole("PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.GET, "/api/exercicios/maquina/{maquinaId}").hasAnyRole("PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.POST, "/api/exercicios").hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.PUT, "/api/exercicios/{id}").hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/exercicios/{id}").hasRole("PROFESSOR")

                    //FichaTreinoController
                    .requestMatchers(HttpMethod.GET, "/api/fichas-treino").hasAnyRole("PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.GET, "/api/fichas-treino/{id}").hasAnyRole("PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.POST, "/api/fichas-treino").hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.PUT, "/api/fichas-treino/{id}").hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/fichas-treino/{id}").hasRole("PROFESSOR")

                    //MaquinaController
                    .requestMatchers(HttpMethod.GET, "/api/maquinas").hasAnyRole("ADMIN", "PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.GET, "/api/maquinas/{id}").hasAnyRole("ADMIN", "PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.POST, "/api/maquinas").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/maquinas/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/maquinas/{id}").hasRole("ADMIN")

                    //DispositivoIotController
                    .requestMatchers(HttpMethod.GET, "/api/dispositivos-iot").hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/dispositivos-iot/{id}").hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.POST, "/api/dispositivos-iot").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/dispositivos-iot/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/dispositivos-iot/{id}").hasRole("ADMIN")

                    //PlanoController
                    .requestMatchers(HttpMethod.GET, "/api/planos").hasAnyRole("ADMIN", "PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.GET, "/api/planos/{id}").hasAnyRole("ADMIN", "PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.POST, "/api/planos").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/planos/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/planos/{id}").hasRole("ADMIN")

                    //UnidadeController
                    .requestMatchers(HttpMethod.GET, "/api/unidades").hasAnyRole("ADMIN", "PROFESSOR", "ALUNO")
                    .requestMatchers(HttpMethod.POST, "/api/unidades").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/unidades/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/unidades/{id}").hasRole("ADMIN")

                    // AulaColetivaController
                    .requestMatchers(HttpMethod.POST, "/api/aulas-coletivas").hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.PUT, "/api/aulas-coletivas/{id}").hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/aulas-coletivas/{id}").hasRole("PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/api/aulas-coletivas/semana").hasAnyRole("PROFESSOR", "ALUNO")

                    // AgendamentoController
                    .requestMatchers(HttpMethod.POST, "/api/agendamentos").hasRole("ALUNO")
                    .requestMatchers(HttpMethod.DELETE, "/api/agendamentos/{id}").hasRole("ALUNO")

                    .requestMatchers(HttpMethod.GET, "/api/usuarios/me").authenticated()

                    .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
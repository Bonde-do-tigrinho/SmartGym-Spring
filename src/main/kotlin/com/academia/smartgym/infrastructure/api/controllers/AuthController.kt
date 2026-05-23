package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.AuthUseCase
import com.academia.smartgym.domain.model.AuthRequest
import com.academia.smartgym.domain.model.AuthResponse
import com.academia.smartgym.domain.model.RegisterRequest
import com.academia.smartgym.domain.model.Usuario
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authUseCase: AuthUseCase
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authUseCase.login(request))
    }


    @PostMapping("/register")
    fun registrar(@Valid @RequestBody request: RegisterRequest): ResponseEntity<Map<String, Any>> {
        authUseCase.registrar(request)
        return ResponseEntity.ok(mapOf(
            "sucesso" to true,
            "mensagem" to "Cadastro realizado! Verifique seu email para ativar sua conta."
        ))
    }

    @GetMapping("/verificar")
    fun verificarEmail(@RequestParam token: String): ResponseEntity<String> {
        val resultado = authUseCase.verificarEmail(token)
        return ResponseEntity.ok(resultado)
    }

    @PostMapping("/reenviar-verificacao")
    fun reenviarVerificacao(@RequestBody body: Map<String, String>): ResponseEntity<String> {
        val email = body["email"] ?: return ResponseEntity.badRequest().body("Email não informado")
        val resultado = authUseCase.reenviarVerificacao(email)
        return ResponseEntity.ok(resultado)
    }

    @PostMapping("/recuperar-senha")
    fun recuperarSenha(@RequestBody body: Map<String, String>): ResponseEntity<String> {
        val email = body["email"] ?: return ResponseEntity.badRequest().body("Email não informado")
        val resultado = authUseCase.solicitarRecuperacaoSenha(email)
        return ResponseEntity.ok(resultado)
    }

    @PostMapping("/resetar-senha")
    fun resetarSenha(@RequestParam token: String, @RequestBody body: Map<String, String>): ResponseEntity<String> {
        val novaSenha = body["novaSenha"] ?: return ResponseEntity.badRequest().body("Nova senha não informada")
        val resultado = authUseCase.resetarSenha(token, novaSenha)
        return ResponseEntity.ok(resultado)
    }

    @GetMapping("/resetar-senha")
    fun paginaResetarSenha(@RequestParam token: String): ResponseEntity<String> {
        val html = """  
        <!DOCTYPE html>  
        <html lang="pt-BR">  
        <head>  
            <meta charset="UTF-8">  
            <meta name="viewport" content="width=device-width, initial-scale=1.0">  
            <title>Redefinir Senha - SmartGym</title>  
            <style>  
                * { margin: 0; padding: 0; box-sizing: border-box; }  
                body {  
                    font-family: Arial, sans-serif;  
                    background-color: #0F0F0F;  
                    color: #FFFFFF;  
                    display: flex;  
                    justify-content: center;  
                    align-items: center;  
                    min-height: 100vh;  
                    padding: 20px;  
                }  
                .card {  
                    background-color: #1A1A1A;  
                    border-radius: 16px;  
                    padding: 40px 32px;  
                    max-width: 400px;  
                    width: 100%;  
                    text-align: center;  
                }  
                .logo {  
                    font-size: 28px;  
                    font-weight: 900;  
                    color: #D9FF00;  
                    letter-spacing: 2px;  
                    margin-bottom: 8px;  
                }  
                .subtitle {  
                    color: #8E8E8E;  
                    font-size: 14px;  
                    margin-bottom: 32px;  
                }  
                h2 {  
                    font-size: 22px;  
                    font-weight: 700;  
                    margin-bottom: 12px;  
                }  
                p {  
                    color: #8E8E8E;  
                    font-size: 14px;  
                    line-height: 1.6;  
                    margin-bottom: 32px;  
                }  
                .btn {  
                    display: block;  
                    background-color: #D9FF00;  
                    color: #0F0F0F;  
                    padding: 16px 24px;  
                    border-radius: 12px;  
                    text-decoration: none;  
                    font-weight: 700;  
                    font-size: 16px;  
                    margin-bottom: 16px;  
                    transition: opacity 0.2s;  
                }  
                .btn:hover { opacity: 0.85; }  
                .btn-secondary {  
                    display: block;  
                    background-color: transparent;  
                    color: #8E8E8E;  
                    padding: 12px 24px;  
                    border-radius: 12px;  
                    text-decoration: none;  
                    font-size: 14px;  
                    border: 1px solid #2A2A2A;  
                }  
                .warning {  
                    color: #FF6B6B;  
                    font-size: 12px;  
                    margin-top: 24px;  
                }  
            </style>  
        </head>  
        <body>  
            <div class="card">  
                <div class="logo">SMARTGYM</div>  
                <div class="subtitle">Sua academia inteligente 💪</div>  
                <h2>Redefinir Senha</h2>  
                <p>Clique no botão abaixo para abrir o app SmartGym e criar sua nova senha.</p>  
                <a href="smartgym://resetar-senha?token=$token" class="btn">  
                    Abrir no App  
                </a>  
                <a href="smartgym://resetar-senha?token=$token" class="btn-secondary">  
                    Não abriu? Toque aqui novamente  
                </a>  
                <p class="warning">⚠️ Este link expira em 1 hora.</p>  
            </div>  
        </body>  
        </html>  
    """.trimIndent()

        return ResponseEntity.ok()
            .header("Content-Type", "text/html; charset=UTF-8")
            .body(html)
    }
}
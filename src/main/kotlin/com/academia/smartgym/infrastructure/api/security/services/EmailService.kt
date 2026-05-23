package com.academia.smartgym.infrastructure.api.security.services

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {

    fun enviarBoasVindas(nome: String, email: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(email)
        helper.setSubject("Bem-vindo à SmartGym! 🏋️")
        helper.setText(
            """  
            <html>  
                <body style="font-family: Arial, sans-serif; background-color: #0F0F0F; color: #FFFFFF; padding: 20px;">  
                    <h1 style="color: #D9FF00;">Bem-vindo à SmartGym, $nome! 💪</h1>  
                    <p>Sua conta foi criada com sucesso!</p>  
                    <p>Você está no plano <strong style="color: #D9FF00;">Basic</strong> (gratuito).</p>  
                    <p>Acesse o app e comece sua jornada!</p>  
                    <br/>  
                    <p style="color: #8E8E8E;">Equipe SmartGym</p>  
                </body>  
            </html>  
            """.trimIndent(),
            true
        )

        mailSender.send(message)
    }

    fun enviarSenhaParaNovoUsuario(nome: String, email: String, senha: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(email)
        helper.setSubject("Sua conta na SmartGym foi criada! 🏋️")
        helper.setText(
            """
        <html>
            <body style="font-family: Arial, sans-serif; background-color: #0F0F0F; color: #FFFFFF; padding: 20px;">
                <h1 style="color: #D9FF00;">Olá, $nome! 💪</h1>
                <p>O administrador criou uma conta para você na <strong>SmartGym</strong>.</p>
                <p>Seus dados de acesso:</p>
                <ul>
                    <li><strong>Email:</strong> $email</li>
                    <li><strong>Senha:</strong> <span style="color: #D9FF00; font-size: 18px;">$senha</span></li>
                </ul>
                <p style="color: #8E8E8E;">Recomendamos que você altere sua senha após o primeiro acesso.</p>
                <br/>
                <p style="color: #8E8E8E;">Equipe SmartGym</p>
            </body>
        </html>
        """.trimIndent(),
            true
        )

        mailSender.send(message)
    }

    fun enviarVerificacaoEmail(nome: String, email: String, token: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        val link = "http://localhost:8080/api/auth/verificar?token=$token"

        helper.setFrom("equipesmartgym@gmail.com")
        helper.setTo(email)
        helper.setSubject("Verifique seu email - SmartGym 🏋️")
        helper.setText(
            """
        <html>
            <body style="font-family: Arial, sans-serif; background-color: #0F0F0F; color: #FFFFFF; padding: 20px;">
                <h1 style="color: #D9FF00;">Olá, $nome! 💪</h1>
                <p>Obrigado por se cadastrar na <strong>SmartGym</strong>.</p>
                <p>Clique no botão abaixo para verificar seu email:</p>
                <a href="$link" 
                   style="display: inline-block; background-color: #D9FF00; color: #0F0F0F; 
                          padding: 12px 24px; border-radius: 8px; text-decoration: none; 
                          font-weight: bold; margin-top: 16px;">
                    Verificar Email
                </a>
                <p style="color: #8E8E8E; margin-top: 24px;">Este link expira em 24 horas.</p>
                <p style="color: #8E8E8E;">Se você não criou uma conta, ignore este email.</p>
            </body>
        </html>
        """.trimIndent(),
            true
        )

        mailSender.send(message)
    }

    fun enviarRecuperacaoSenha(nome: String, email: String, token: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        val link = "http://localhost:8080/api/auth/resetar-senha?token=\$token"

        helper.setFrom("equipesmartgym@gmail.com")
        helper.setTo(email)
        helper.setSubject("Recuperação de Senha - SmartGym 🏋️")
        helper.setText(
            """  
        <html>  
            <body style="font-family: Arial, sans-serif; background-color: #0F0F0F; color: #FFFFFF; padding: 20px;">  
                <h1 style="color: #D9FF00;">Olá, $nome! 💪</h1>  
                <p>Recebemos uma solicitação para redefinir a senha da sua conta na <strong>SmartGym</strong>.</p>  
                <p>Clique no botão abaixo para criar uma nova senha:</p>  
                <a href="$link"   
                   style="display: inline-block; background-color: #D9FF00; color: #0F0F0F;   
                          padding: 12px 24px; border-radius: 8px; text-decoration: none;   
                          font-weight: bold; margin-top: 16px;">  
                    Redefinir Senha  
                </a>  
                <p style="color: #8E8E8E; margin-top: 24px;">Este link expira em 1 hora.</p>  
                <p style="color: #8E8E8E;">Se você não solicitou a recuperação, ignore este email.</p>  
            </body>  
        </html>  
        """.trimIndent(),
            true
        )

        mailSender.send(message)
    }
}
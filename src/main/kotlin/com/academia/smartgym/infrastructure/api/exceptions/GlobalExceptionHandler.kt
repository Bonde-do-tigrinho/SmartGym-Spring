package com.academia.smartgym.infrastructure.api.exceptions

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(
        ex: RuntimeException,
        request: HttpServletRequest
    ): ResponseEntity<Map<String, Any>> {
        // 💡 Log no console do backend para você ver o erro em tempo real
        println("🚨 [BACKEND 400] RuntimeException capturada: ${ex.message}")
        ex.printStackTrace()

        val body = linkedMapOf<String, Any>(
            "timestamp" to LocalDateTime.now().toString(),
            "status" to HttpStatus.BAD_REQUEST.value(),
            "message" to (ex.message ?: "Erro desconhecido"),
            "path" to request.requestURI
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<Map<String, Any>> {
        val errors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Valor inválido")
        }

        println("🚨 [BACKEND 400] Erro de Validação nos campos: $errors")

        val body = linkedMapOf<String, Any>(
            "timestamp" to LocalDateTime.now().toString(),
            "status" to HttpStatus.BAD_REQUEST.value(),
            "errors" to errors,
            "path" to request.requestURI
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonErrors(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<Map<String, Any>> {
        println("🚨 [BACKEND 400] JSON enviado pelo Ktor está incompatível ou malformado:")
        println(ex.message)

        val body = linkedMapOf<String, Any>(
            "timestamp" to LocalDateTime.now().toString(),
            "status" to HttpStatus.BAD_REQUEST.value(),
            "message" to "JSON malformado ou campos com tipos incorretos.",
            "details" to (ex.message ?: "Erro de leitura de corpo"),
            "path" to request.requestURI
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}
package com.academia.smartgym.infrastructure.api.exceptions

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

        val body = linkedMapOf<String, Any>(
            "timestamp" to LocalDateTime.now().toString(),
            "status" to HttpStatus.BAD_REQUEST.value(),
            "message" to (ex.message ?: "Erro desconhecido"),
            "path" to request.requestURI
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}
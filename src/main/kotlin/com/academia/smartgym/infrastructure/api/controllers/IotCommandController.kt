package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.IotCommandUseCase
import com.academia.smartgym.domain.model.IotCommand
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/iot")
class IotCommandController(
    private val iotCommandUseCase: IotCommandUseCase
) {

    @PostMapping("/devices/{deviceId}/commands")
    fun sendCommand(
        @PathVariable deviceId: String,
        @Valid @RequestBody body: IotCommand
    ): Map<String, String> {
        val topic = iotCommandUseCase.enviarComando(deviceId, body)
        return mapOf(
            "status" to "sent",
            "topic" to topic
        )
    }
}

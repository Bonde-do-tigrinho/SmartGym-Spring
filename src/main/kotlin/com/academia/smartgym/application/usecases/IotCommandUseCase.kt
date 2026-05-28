package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.IotCommand
import com.academia.smartgym.domain.repository.IotMessageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import tools.jackson.databind.ObjectMapper

@Service
class IotCommandUseCase(
    private val iotMessageRepository: IotMessageRepository,
    private val objectMapper: ObjectMapper,
    @Value("\${app.mqtt.topic.command-prefix}") private val commandPrefix: String
) {
    fun enviarComando(deviceId: String, comando: IotCommand): String {
        val topic = "$commandPrefix/$deviceId/cmd"
        val payload = objectMapper.writeValueAsString(comando)
        iotMessageRepository.publish(topic, payload)
        return topic
    }
}


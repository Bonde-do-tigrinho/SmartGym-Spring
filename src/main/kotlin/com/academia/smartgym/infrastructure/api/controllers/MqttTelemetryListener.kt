package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.MaquinaIotUseCase
import com.academia.smartgym.domain.model.StatusMaquinaIot
import tools.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

@Component
class MqttTelemetryListener(
    private val maquinaIotUseCase: MaquinaIotUseCase,
    private val objectMapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger(MqttTelemetryListener::class.java)

    // Expects topic format: smartgym/device/{deviceId}/telemetry
    // Payload: {"status": "detectado", "sensor": "..."} or {"status": "livre", "sensor": "..."}
    @ServiceActivator(inputChannel = "mqttInputChannel")
    fun handle(message: Message<*>) {
        val topic = message.headers["mqtt_receivedTopic"]?.toString().orEmpty()
        val payload = message.payload?.toString().orEmpty()
        logger.info("MQTT telemetria recebida | topic={} | payload={}", topic, payload)

        try {
            // Extract deviceId from topic: smartgym/device/{deviceId}/telemetry
            val parts = topic.split("/")
            if (parts.size < 4 || parts[0] != "smartgym" || parts[1] != "device") {
                logger.warn("Tópico MQTT fora do padrão esperado: {}", topic)
                return
            }
            val deviceId = parts[2]

            // Parse JSON payload
            val jsonNode = objectMapper.readTree(payload)
            val statusStr = jsonNode.get("status")?.asText() ?: run {
                logger.warn("Payload sem campo 'status': {}", payload)
                return
            }

            // Map ESP32 status to domain enum
            val newStatus = when (statusStr.lowercase()) {
                "detectado" -> StatusMaquinaIot.OCUPADA
                "livre"     -> StatusMaquinaIot.LIVRE
                else -> {
                    logger.warn("Status desconhecido recebido via MQTT: {}", statusStr)
                    return
                }
            }

            val updated = maquinaIotUseCase.updateStatusByDeviceId(deviceId, newStatus)
            if (updated != null) {
                logger.info("Status da máquina '{}' (deviceId={}) atualizado para {}", updated.nome, deviceId, newStatus)
            } else {
                logger.warn("Nenhuma MaquinaIot cadastrada com deviceId='{}'. Registre a máquina via POST /api/maquinas-iot", deviceId)
            }
        } catch (e: Exception) {
            logger.error("Erro ao processar telemetria MQTT do tópico '{}': {}", topic, e.message)
        }
    }
}

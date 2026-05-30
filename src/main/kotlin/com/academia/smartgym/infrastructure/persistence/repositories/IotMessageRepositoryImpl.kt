package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.repository.IotMessageRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.integration.mqtt.support.MqttHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Repository

@Repository
class IotMessageRepositoryImpl(
    @Qualifier("mqttOutboundChannel")
    private val mqttOutboundChannel: MessageChannel
) : IotMessageRepository {

    override fun publish(topic: String, payload: String) {
        val message: Message<String> = MessageBuilder
            .withPayload(payload)
            .setHeader(MqttHeaders.TOPIC, topic)
            .build()
        mqttOutboundChannel.send(message)
    }
}


package com.academia.smartgym.infrastructure.config

import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.core.MessageProducer
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler

@Configuration
class MqttConfig(
    @Value("\${app.mqtt.url}") private val brokerUrl: String,
    @Value("\${app.mqtt.client-id}") private val clientId: String,
    @Value("\${app.mqtt.username:}") private val brokerUsername: String,
    @Value("\${app.mqtt.password:}") private val brokerPassword: String,
    @Value("\${app.mqtt.topic.telemetry}") private val telemetryTopic: String
) {

    @Bean
    fun mqttClientFactory(): MqttPahoClientFactory {
        val factory = DefaultMqttPahoClientFactory()
        val options = MqttConnectOptions().apply {
            serverURIs = arrayOf(brokerUrl)
            isAutomaticReconnect = true
            isCleanSession = false
            connectionTimeout = 10
            keepAliveInterval = 20
            if (brokerUsername.isNotBlank()) userName = brokerUsername
            if (brokerPassword.isNotBlank()) this.password = brokerPassword.toCharArray()
        }
        factory.connectionOptions = options
        return factory
    }

    @Bean
    fun mqttInputChannel(): MessageChannel = DirectChannel()

    @Bean
    fun inbound(): MessageProducer {
        val adapter = MqttPahoMessageDrivenChannelAdapter(
            "$clientId-in",
            mqttClientFactory(),
            telemetryTopic
        )
        adapter.setCompletionTimeout(5000)
        adapter.setQos(1)
        adapter.setConverter(DefaultPahoMessageConverter())
        adapter.setOutputChannel(mqttInputChannel())
        return adapter
    }

    @Bean
    fun mqttOutboundChannel(): MessageChannel = DirectChannel()

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    fun mqttOutbound(): MessageHandler {
        val handler = MqttPahoMessageHandler("$clientId-out", mqttClientFactory())
        handler.setAsync(true)
        handler.setDefaultQos(1)
        return handler
    }
}



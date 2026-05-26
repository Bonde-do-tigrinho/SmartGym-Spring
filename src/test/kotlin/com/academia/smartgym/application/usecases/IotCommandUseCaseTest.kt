package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.IotCommand
import com.academia.smartgym.domain.repository.IotMessageRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import tools.jackson.databind.ObjectMapper
import kotlin.test.assertEquals

class IotCommandUseCaseTest {

    private val repository = mock<IotMessageRepository>()
    private val objectMapper = ObjectMapper()

    @Test
    fun `deve publicar comando no topico do dispositivo`() {
        val useCase = IotCommandUseCase(repository, objectMapper, "smartgym/device")

        val topic = useCase.enviarComando("esp32-001", IotCommand(command = "led", value = "on"))

        val topicCaptor = argumentCaptor<String>()
        val payloadCaptor = argumentCaptor<String>()
        verify(repository).publish(topicCaptor.capture(), payloadCaptor.capture())

        assertEquals("smartgym/device/esp32-001/cmd", topic)
        assertEquals("smartgym/device/esp32-001/cmd", topicCaptor.firstValue)
        assertEquals("{\"command\":\"led\",\"value\":\"on\"}", payloadCaptor.firstValue)
    }
}



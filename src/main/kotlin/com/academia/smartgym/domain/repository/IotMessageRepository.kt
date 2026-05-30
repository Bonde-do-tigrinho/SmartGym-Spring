package com.academia.smartgym.domain.repository

interface IotMessageRepository {
    fun publish(topic: String, payload: String)
}

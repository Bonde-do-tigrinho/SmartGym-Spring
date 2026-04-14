package com.academia.smartgym.infrastructure.persistence.mappers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExercicioMapperConfig {
    @Bean
    fun exercicioMapper() = ExercicioMapper()
}


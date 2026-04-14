package com.academia.smartgym.infrastructure.persistence.mappers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MaquinaMapperConfig {
    @Bean
    fun maquinaMapper() = MaquinaMapper()
}

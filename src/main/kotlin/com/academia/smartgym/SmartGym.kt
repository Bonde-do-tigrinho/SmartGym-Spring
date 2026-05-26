package com.academia.smartgym

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.integration.config.EnableIntegration

@EnableIntegration
@SpringBootApplication
class SmartGym

fun main(args: Array<String>) {
	runApplication<SmartGym>(*args)
}
package com.academia.smartgym

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SmartGym

fun main(args: Array<String>) {
	runApplication<com.academia.smartgym.SmartGym>(*args)
}
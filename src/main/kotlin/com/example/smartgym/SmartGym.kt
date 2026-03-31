package com.example.smartgym

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SmartGym

fun main(args: Array<String>) {
	runApplication<SmartGym>(*args)
}
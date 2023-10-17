package br.com.fiap.lanchonete

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LanchoneteApplication

fun main(args: Array<String>) {
    runApplication<LanchoneteApplication>(*args)
}

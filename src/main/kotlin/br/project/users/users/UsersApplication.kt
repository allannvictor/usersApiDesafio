package br.project.users.users

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableCaching
class UsersApplication

fun main(args: Array<String>) {
	runApplication<UsersApplication>(*args)
}

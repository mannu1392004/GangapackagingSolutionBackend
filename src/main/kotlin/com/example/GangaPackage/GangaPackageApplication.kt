package com.example.GangaPackage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class GangaPackageApplication

fun main(args: Array<String>) {
	runApplication<GangaPackageApplication>(*args)
}
package com.example.GangaPackage.repositories

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.MongoRepository

interface Price : MongoRepository<Pricing, String> {
}

data class Pricing(
    @Id
    val id: String,
    val price: String,
)
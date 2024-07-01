package com.example.GangaPackage.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@Document(collection = "tokenTime")
data class tokenTimeStore(
    @Id
    val mobile: String,
    val time: Date,
)

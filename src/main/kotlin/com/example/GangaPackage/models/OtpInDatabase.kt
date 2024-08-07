package com.example.GangaPackage.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("OtpDataBase")
data class OtpInDatabase(
    val otp: Int,
    val time:LocalDateTime ,
    @Id
    val mobile: String
)

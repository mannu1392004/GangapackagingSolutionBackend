package com.example.GangaPackage.repositories

import com.example.GangaPackage.models.OtpInDatabase
import org.springframework.data.mongodb.repository.MongoRepository

interface OtpRepository :MongoRepository<OtpInDatabase, String> {
}
package com.example.GangaPackage.config

import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate


@Configuration
class mongodbConfig
{
    @Bean
    fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(MongoClients.create("mongodb://localhost:27017"), "GangaPackagerBackend")
    }
}
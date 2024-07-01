package com.example.GangaPackage.repositories

import com.example.GangaPackage.models.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepositories:MongoRepository<User,String> {

}
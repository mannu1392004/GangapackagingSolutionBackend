package com.example.GangaPackage.repositories

import com.example.GangaPackage.models.tokenTimeStore
import org.springframework.data.mongodb.repository.MongoRepository

interface UserTokenTime:MongoRepository<tokenTimeStore,String>  {

}
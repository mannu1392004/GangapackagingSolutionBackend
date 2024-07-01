package com.example.GangaPackage.services

import com.example.GangaPackage.models.OtpInDatabase
import com.example.GangaPackage.models.OtpInput
import com.example.GangaPackage.models.User
import com.example.GangaPackage.models.tokenTimeStore
import com.example.GangaPackage.repositories.OtpRepository
import com.example.GangaPackage.repositories.UserRepositories
import com.example.GangaPackage.repositories.UserTokenTime
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UserServices(
    private val userRepositories: UserRepositories,
    private val otpRepository: OtpRepository,
    private val UserTokenTime: UserTokenTime,
) {

    fun save(user: User) {
        this.userRepositories.save(user)
    }

    fun saveOtp(otpInDatabase: OtpInDatabase) {
        println(otpInDatabase)
        this.otpRepository.save(otpInDatabase)
    }

    fun verifyOtp(otpInput: OtpInput): Boolean {
        val otpValue = this.otpRepository.findById(otpInput.mobile.toLong()).get()
        val time = LocalDateTime.now()
        if (otpValue.time.plusMinutes(5).isAfter(time) && otpValue.otp == otpInput.otp.toInt()) {
            return true
        }
        return false
    }

// find user by id
    fun findUserById(id: String): User {
        return this.userRepositories.findById(id).get()
    }

    // check is user exist or not
    fun isNewUser(mobile: String): Boolean {
        return this.userRepositories.existsById(mobile)
    }

}
package com.example.GangaPackage.controllers

import com.example.GangaPackage.models.OtpInDatabase
import com.example.GangaPackage.models.OtpInput
import com.example.GangaPackage.models.User
import com.example.GangaPackage.models.createNewUser.NewUser
import com.example.GangaPackage.services.SmsService
import com.example.GangaPackage.services.UserServices
import com.example.GangaPackage.util.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import kotlin.random.Random

@RestController
class AuthController(
    private val userServices: UserServices,
    private val jwtUtil: JwtUtil,
) {

    @Autowired
    private val mongoTemplate: MongoTemplate? = null

    @Autowired
    private lateinit var smsService: SmsService

    @PostMapping("/OtpRequest")
    fun otpRequest(@RequestBody number: String): String {
        val otp = Random.nextInt(100000, 999999)
        val message =
            "Hello\n This is  your OTP is $otp \nFrom Ganga Package Solution"

        smsService.sendSms(number, message)
        userServices.saveOtp(
            OtpInDatabase(
                otp = otp,
                mobile = number.toLong(),
                time = LocalDateTime.now()
            )
        )
        return number
    }

    @PostMapping("/OtpVerify")
    fun otpVerify(@RequestBody otpInput: OtpInput): String {
        if (userServices.verifyOtp(otpInput)) {
            return jwtUtil.generateToken(otpInput.mobile)
        }
        return "Not Verified"
    }

    // checking if is user is a new user or not
    @GetMapping("/isNewUser/{mobile}")
    fun isNewUser(@PathVariable("mobile") mobile: String): Boolean {
        return !userServices.isNewUser(mobile)
    }

    // creating a new user
    @PostMapping("/createNewUser/{jwtToken}")
    fun createNewUser(@RequestBody user: NewUser, @PathVariable("jwtToken") jwtToken: String): Boolean {
        // creating a new user
        val username = jwtUtil.extractUsername(jwtToken)
        if (user.mobileNo == username) {

            userServices.save(
                User(
                    name = user.name,
                    id = user.mobileNo,
                    gmail = user.email,
                    quotationList = emptyList(),
                    bill = emptyList(),
                    companyName = user.companyName
                )
            )
            return true
        }
        else{
            return false
        }
    }

    @GetMapping("hello")
    fun hello(): ResponseEntity<String> {
        return ResponseEntity.ok("This Backend is made by Mannu")
    }


    /*
    // updating all existing users with default address
    fun updateExistingUsersWithDefaultAddress() {
        val query = Query()
        val update = Update().set("address", "Default Address")
        mongoTemplate?.updateMulti(query, update, User::class.java)
    }
*/

}


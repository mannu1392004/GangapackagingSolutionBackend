package com.example.GangaPackage.controllers

import com.example.GangaPackage.models.OtpInDatabase
import com.example.GangaPackage.models.OtpInput
import com.example.GangaPackage.models.User
import com.example.GangaPackage.models.UserDetails
import com.example.GangaPackage.models.createNewUser.NewUser
import com.example.GangaPackage.services.EmailService
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

    @Autowired
    private lateinit var emailService: EmailService

    @PostMapping("/OtpRequest")
    fun otpRequest(@RequestBody number: String): String {
        val otp = Random.nextInt(100000, 999999)
        val message =
            "Hello\n This is  your OTP is $otp \nFrom Ganga Package Solution"

        emailService?.sendOtp(number, otp.toString())

        userServices.saveOtp(
            OtpInDatabase(
                otp = otp,
                mobile = number,
                time = LocalDateTime.now()
            )
        )
        return number
    }


    @PostMapping("/OtpVerify")
    fun otpVerify(@RequestBody otpInput: OtpInput): otpsend {
        if (userServices.verifyOtp(otpInput)) {
            return otpsend(
                jwtUtil.generateToken(otpInput.mobile),
                !userServices.isNewUser(otpInput.mobile)
            )
        }
        return (
                otpsend(
                    "Invalid Otp",
                    false
                )
                )

    }


    // creating a new user
    @PostMapping("/createNewUser/{jwtToken}")
    fun createNewUser(@RequestBody user: NewUser, @PathVariable("jwtToken") jwtToken: String): String {
        // creating a new user
        val username = jwtUtil.extractUsername(jwtToken)
        if (user.email == username) {
            userServices.save(
                User(
                    name = user.name,
                    id = user.email,
                    gmail = user.email,
                    quotationList = emptyList(),
                    bill = emptyList(),
                    companyName = user.companyName,
                    packagingList = emptyList(),
                    totalQuotation = 0,
                    totalPackagingList = 0,
                    lrBills = emptyList(),
                    totalLrBills = 0,
                    moneyReceipt = emptyList(),
                    totalMoneyReceipt = 0,
                    totalBills = 0,
                    subscribed = false,
                    adress = user.address,
                    bankAccount = user.bankAccount,
                    ifscCode = user.ifscCode,
                    mobileNumber = user.mobileNo
                )
            )
            return "T"
        } else {
            println("User Not Created")
            return "F"
        }
    }

    @GetMapping("hello")
    fun hello(): ResponseEntity<String> {
        return ResponseEntity.ok("This Backend is made by Mannu")
    }

    @GetMapping("jwt")
    fun jwt(): ResponseEntity<String> {
        val x = jwtUtil.generateToken("+917015932229")
        return ResponseEntity.ok(x)
    }

    // sending user details
    @GetMapping("/getUserDetails/{jwtToken}")
    fun getUserDetails(@PathVariable("jwtToken") jwtToken: String): UserDetails {
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        val send = UserDetails(
            name = user.name,
            companyName = user.companyName,
            totalQuotation = user.totalQuotation.toString(),
            totalMoneyReceipt = user.totalMoneyReceipt.toString(),
            totalBill = user.totalMoneyReceipt.toString(),
            phoneNumber = user.mobileNumber,
            subscribed = user.subscribed,
            companyLogo = "data:image/png;base64,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",
            subscription = userServices.findPrice(),
            signature = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.morebusiness.com%2Fbenefits-of-adding-a-handwritten-digital-signature-to-business-emails%2F&psig=AOvVaw0fWhfBKhDflPQC1NI5H8RT&ust=1722253442980000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCKiez5rUyYcDFQAAAAAdAAAAABAE"
        )
        return send
    }


    @GetMapping("/re", produces = ["application/json"])
    fun re():UserSave{
        return UserSave(true)
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

// new user or not
data class otpsend(
    val jwt: String,
    val newUser: Boolean
)

data class UserSave(
    val done: Boolean
)
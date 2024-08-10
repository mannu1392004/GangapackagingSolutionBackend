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
                    mobileNumber = user.mobileNo,
                    qrCode = "",
                    profile = "",
                    signature = ""
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
            companyLogo =user.profile,
            subscription = userServices.findPrice(),
            signature = user.signature,
            qrCode = user.qrCode)
        return send
    }


    @GetMapping("/re", produces = ["application/json"])
    fun re():String{
        return jwtUtil.generateToken("mannu1392004@gmail.com")
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
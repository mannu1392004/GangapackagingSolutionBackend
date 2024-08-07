package com.example.GangaPackage.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(@Autowired private val mailSender: JavaMailSender) {
    fun sendOtp(
        to: String,
        otp: String
    ){
        println(to)
        val message = SimpleMailMessage()
        message.from = "biltymovers@gmail.com"
        message.setTo(to)
        message.subject = "Your OTP Code"
        message.text = "Your OTP code is $otp. It is valid for 5 minutes."
        mailSender.send(message)
    }
}
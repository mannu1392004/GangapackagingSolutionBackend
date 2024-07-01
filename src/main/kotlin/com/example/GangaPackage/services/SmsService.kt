package com.example.GangaPackage.services

import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.springframework.stereotype.Service


@Service
class SmsService {

    fun sendSms(phoneNumber: String, message: String) {
        Message.creator(

            PhoneNumber(phoneNumber),
            PhoneNumber("+17203582985"),
            message
        ).create()
    }

}
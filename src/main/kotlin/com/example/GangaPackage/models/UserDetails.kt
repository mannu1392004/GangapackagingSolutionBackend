package com.example.GangaPackage.models

import com.example.GangaPackage.models.moneyReciept.MoneyReceipt
import com.example.GangaPackage.repositories.Pricing
import com.twilio.type.PhoneNumber

data class UserDetails(
    val name: String,
    val totalBill: String,
    val totalQuotation: String,
    val companyName: String,
    val totalMoneyReceipt: String,
    val phoneNumber: String,
    val signature: String,
    val companyLogo:String,
    val subscribed:Boolean,
    val subscription:List<Pricing>
    )

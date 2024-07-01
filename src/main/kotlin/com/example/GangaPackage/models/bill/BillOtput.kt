package com.example.GangaPackage.models.bill

import java.time.LocalDate

data class BillOtput(
    val billingDate:String,
    val status:Boolean,
    val billNo:String,
    val BillingName:String,
    val from :String,
    val to:String,
    val total:String,
    val call:String

)

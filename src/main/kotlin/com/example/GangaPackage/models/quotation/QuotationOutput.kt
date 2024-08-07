package com.example.GangaPackage.models.quotation

import java.time.LocalDate

data class QuotationOutput(
    val id: Int?,
    val status: String?,
    val markAsPaid: Boolean,
    val partyNAme: String?,
    val from: String?,
    val to: String?,
    val total: String?,
    val packagingDate :String,
    val shiftingDate:String,
    val gstin:Double,
    val insurance:Double,
    val subcharges:Double?
)

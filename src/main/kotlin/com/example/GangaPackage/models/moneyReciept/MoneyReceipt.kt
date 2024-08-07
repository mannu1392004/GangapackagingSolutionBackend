package com.example.GangaPackage.models.moneyReciept
data class MoneyReceipt(
    var id: Int,
    val receiptNumber: String,
    val date: String,
    val name: String,
    val phone: String,
    val receiptAgainst: String,
    val number: String,
    val billQuotationDate: String,
    val moveFrom: String,
    val moveTo: String,
    val paymentType: String,
    val receiptAmount: String,
    val paymentMode: String,
    val transactionsNumber: String,
    val branch: String,
    val remarks: String
)
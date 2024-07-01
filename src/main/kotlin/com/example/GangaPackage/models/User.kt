package com.example.GangaPackage.models

import com.example.GangaPackage.models.bill.Bill
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "Users")
data class User(
    @Id
    var id: String,
    val name: String,
    val gmail: String,
    var quotationList:List<Quotation>,
    var bill:List<Bill>,
    val companyName :String
)
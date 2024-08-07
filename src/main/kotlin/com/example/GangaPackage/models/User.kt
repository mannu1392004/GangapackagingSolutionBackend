package com.example.GangaPackage.models

import com.example.GangaPackage.models.bill.Bill
import com.example.GangaPackage.models.lrbilty.LrBilty
import com.example.GangaPackage.models.moneyReciept.MoneyReceipt
import com.example.GangaPackage.models.packagingList.PackagingList
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "Users")
data class User(
    @Id
    var id: String,
    val name: String,
    val gmail: String,
    val adress:String,
    val bankAccount:String,
    val ifscCode:String,
    var subscribed:Boolean,
    var quotationList: List<Quotation>,
    var bill: List<Bill>,
    var totalBills:Int,
    val companyName: String,
    var packagingList: List<PackagingList>,
    var totalQuotation: Int,
    val mobileNumber:String,
    //package List
    var totalPackagingList: Int,

// lr bilty
    var lrBills: List<LrBilty>,
    var totalLrBills: Int,

    // moneyReceipt

    var moneyReceipt: List<MoneyReceipt>,
    var totalMoneyReceipt: Int,


    )
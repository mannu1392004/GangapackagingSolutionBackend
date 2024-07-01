package com.example.GangaPackage.models.bill

import com.example.GangaPackage.models.quotation.charges

data class Bill(
    val id: Int,
    var companyNameOfParty: String = "",
    var lrNumber: String = "0",
    var invoiceDate: String = "",
    var deleveryDate: String = "",
    var movingPath: String = "By Road",
    var typeOfShipment: String = "Household Goods",
    var moveFrom: String = "",
    var moveTo: String = "",
    var vehicleNo: String = "",
val marksStatus:Boolean = false,
    // billing Details
    var billToName: String = "",
    var billToPhone: String = "",
    var gstin: String = "",
    var country: String = "India",
    var state: String = "",
    val city: String = "",
    val pinCode: String = "",
    val address:String = "",

    // val consigonor details
    var consignorName: String = "",
    var consignorPhone: String = "",
    var gstIn1: String = "",
    var country1: String = "India",
    var state1: String = "",
    val city1: String = "",
    val pinCode1: String = "",
    val address1:String = "",

    // val consignee details
    var consigneeName: String = "",
    var consigneePhone: String = "",
    var gstIn2: String = "",
    var country2: String = "India",
    var state2: String = "",
    val city2: String = "",
    val pinCode2: String = "",
    val address2:String = "",

    // val package details
    var packageName:String = "",
    var description:String = "",
    val totalWeight:String = "",
    val remark:String = "",

    //payment Details
    var freightCharge: String = "0",
    var advancePaid: String = "0",

    var packingChargeType: String = charges.includeinFrieght,
    var packagingCharge: String = "0",

    var unpackingChargeType: String = charges.includeinFrieght,
    var unpackingCharge: String = "0",

    var loadingChargeType: String = charges.includeinFrieght,
    var loadingCharge: String = "0",

    var unloadingChargeType: String = charges.includeinFrieght,
    var unloadingCharge: String = "0",

    var packagingMaterialType: String = charges.includeinFrieght,
    var packingMaterialCharge: String = "0",

    var storageCharge: String = "0",

    var carBikeTpt: String = "0",

    var miscelaneousCharge: String = "0",

    var otherCharge: String = "0",

    var stCharge: String = "0",

    var octroiGreenTAx: String = "0",

    var subchargeType: String = charges.notApplicable,
    var subcharges: String = "10",

    var gstIn: String = charges.inQuotation,
    var gst: String = "18",
    var GstType: String = charges.cgstSgst,


    var remark1: String = "",
    var discount: String = "0",

    //insurance(fov) details
    var insuranceType: String = charges.optional,
    var insuranceCharge: String = "3",
    var gstperc: String = "0",
    var declarationValue: String = "0",

    // vehicle insurance
    var insuranceType1: String = charges.notApplicable,
    var insuranceCharge1: String = "3",
    var gstperc1: String = "0",
    var declarationValue1: String = "0",



    )

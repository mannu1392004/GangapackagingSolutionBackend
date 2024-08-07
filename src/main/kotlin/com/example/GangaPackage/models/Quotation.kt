package com.example.GangaPackage.models

import com.example.GangaPackage.models.quotation.Items_Particulars
import com.example.GangaPackage.models.quotation.charges
import com.twilio.type.PhoneNumber
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*


@Document(collection = "quotations")
data class Quotation(
    @Id
    val id: Int,
    var status: String = "Pending",
    var markAsPAid: Boolean = false,
    var date: String = "",
    var movingType: String = "",
    var companyName: String = "",
    var partyName: String = "",
    var email: String = "",
    val phoneNumber: String = "",
    var qTDate: String = "",
    var packingDate: String = "",
    var shiftingDate: String = "",
    // move from
    var country: String = "India",
    var state: String = "",
    var city: String = "",
    var address: String = "",
    var pinCode: String = "",

    // move to

    var country2: String = "India",
    var state2: String = "",
    var city2: String = "",
    var address2: String = "",
    var pinCode2: String = "",

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
    var subcharges: String = "0",

    var gstIn: String = charges.inQuotation,
    var gst: String = "18",
    var GstType: String = charges.cgstSgst,


    var remark: String = "",
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

    // item particular
    var list: List<Items_Particulars> = listOf(Items_Particulars()),

    // additional  details
    var easyAccessForLoading: String = "Yes",
    var shouldAnyItemGotDown: String = "N/A",
    var areAnyRestrictionLoading: String = "N/A",
    var doesAnySpecialNeedsOrConcerns: String = "N/A",

    var total: String = "",

    )

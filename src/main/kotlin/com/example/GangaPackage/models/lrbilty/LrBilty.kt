package com.example.GangaPackage.models.lrbilty


data class LrBilty(
    var id: Int,

    val lrNumber: String,
    val lrDate: String,
    val riskType: String,

    // truck vehicles details
    val truckNumber: String,
    val moveFrom: String,
    val moveTo: String,

    // driver details
    val driverName: String,
    val driverNumber: String,
    val driverLicense: String,

    //consignor/moveFrom details
    val consignorName: String,
    val consignorNumber: String,
    val gstNumber: String,
    val country: String,
    val state: String,
    val city: String,
    val pincode: String,
    val address: String,

    // consignee/moveTo details
    val consigneeName: String,
    val consigneeNumber: String,
    val gstNumber1: String,
    val country1: String,
    val state1: String,
    val city1: String,
    val pincode1: String,
    val address1: String,

    //packages details
    val numberOfPackages: String,
    val description: String,
    val actualWeight: String,
    val actualweightType: String,
    val chargedWeight: String,
    val chargedWeightType: String,
    val receivePackageCondition: String,
    val remarks: String,

    // payment details
    var freightToBeBilled: String,
    val freightPaid: String,
    val freightBalance: String,
    var totalBasicFreight: String,
    var loadingCharges: String,
    var unloadingCharges: String,
    var stCharges: String,
    var otherCharges: String,
    var lr_cnCharges: String,
    var gstperc: String,
    val gstPaidBy: String,

    // material insurance
    val materialInsurance: String,
    val insuranceCompany: String,
    val policyNumber: String,
    val insuranceDate: String,
    val insuredAmount: String,
    val insuranceRisk: String,

    // demurrage charge
    val demarrageCharge: String,
    val perDayorhour: String,
    val demurageChargeApplicableAfter: String,
    var total:String,
    var subtotal:String,
    var gstsubt :String
)

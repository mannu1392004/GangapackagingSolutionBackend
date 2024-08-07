package com.example.GangaPackage.models.bill

import com.example.GangaPackage.calculations.TotalSubTotal
import com.example.GangaPackage.models.quotation.charges


data class Bill(
    var id: String,
    val billNumber: String,
    val companyName: String,
    val lrNumber: String,
    val invoiceDate: String,
    val deliveryDate: String,
    val movingPath: String,
    val typeOfShipment: String,
    val movingPathRemark: String,
    val moveFrom: String,
    val moveTo: String,
    val vehicleNumber: String,
    // billing details
    val billToName: String,
    val billToPhone: String,
    val gstin: String,
    val country: String,
    val state: String,
    val city: String,
    val pinCode: String,
    val address: String,
    //cionsignor details
    val citionsignorName: String,
    val citionsignorPhone: String,
    val citionsignorGstin: String,
    val citionsignorCountry: String,
    val citionsignorState: String,
    val citionsignorCity: String,
    val citionsignorPinCode: String,
    val citionsignorAddress: String,

    // consignee details
    val consigneeName: String,
    val consigneePhone: String,
    val consigneeGstin: String,
    val consigneeCountry: String,
    val consigneeState: String,
    val consigneeCity: String,
    val consigneePinCode: String,
    val consigneeAddress: String,
    // package details
    val packageName: String,
    val description: String,
    val weight: String,
    val selectedWeight: String,
    val remarks: String,
    // payment details
    var freightCharge: String,
    var advancePaid: String,
    var packingCharge: String,
    var unpackingCharge: String,
    var loadingCharge: String,
    var unloadingCharge: String,
    var packingMaterialCharge: String,
    var StorageCharge: String,
    var carbikeTpt: String,
    var miscellaneousCharge: String,
    var otherCharge: String,
    var stCharge: String,
    var greentax: String,
    val subcharge: String,
    val gstinorex: String,
    var gst: String,
    val gstType: String,
    val reverseCharge: String,
    val gstpaidby: String,
    val paymentRemark: String,
    var discount: String,

    // insurance Details
    val insuranceType: String,
    var insuranceCharge: String,
    var insuranceGst: String,

    //vehicle insurance
    val vehicleInsuranceType: String,
    var vehicleInsuranceCharge: String,
    var vehicleInsuranceGst: String,
    val vehicleInsuranceRemark: String,

    var total: String?,
    var subTotal: String?,
)

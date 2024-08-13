package com.example.GangaPackage.calculations

import com.example.GangaPackage.models.Quotation
import com.example.GangaPackage.models.bill.Bill
import com.example.GangaPackage.models.quotation.QuotationOutput
import com.example.GangaPackage.models.quotation.charges


fun QuotationCalculation(
    quotation: Quotation,
): QuotationOutput {
    val freightCharge = quotation.freightCharge.toDouble()
    val advancePaid = quotation.advancePaid.toDouble()
    var packingCharge = quotation.packagingCharge.toDouble()
    val otherCharge = quotation.otherCharge.toDouble()
    var unpackingCharge = quotation.unpackingCharge.toDouble()
    var loadingCharge = quotation.loadingCharge.toDouble()
    var unloadingCharge = quotation.unloadingCharge.toDouble()
    var packageingMAterialCharge = quotation.packingMaterialCharge.toDouble()
    var storagecharge = quotation.storageCharge.toDouble()
    val carbikeTpt = quotation.carBikeTpt.toDouble()
    val miscleneousCharge = quotation.miscelaneousCharge.toDouble()
    val stCharge = quotation.stCharge.toDouble()
    val octroiGreenTAx = quotation.octroiGreenTAx.toDouble()
    var subcharges = 0.toDouble()
    var gstin = 0.toDouble()

    val discount = quotation.discount.toDouble()

    var insuranceCharge = 0.toDouble()
    var gstperc = quotation.gstperc.toDouble()

    var insuranceCharge1 = 0.toDouble()
    var gstperc1 = quotation.gstperc1.toDouble()

    var total = 0.toDouble()
    total += freightCharge
    total -= discount
    total += packingCharge
    total += otherCharge
    total += unpackingCharge
    total += loadingCharge
    total += unloadingCharge
    total += packageingMAterialCharge
    total += storagecharge
    total += carbikeTpt
    total += miscleneousCharge
    total += stCharge
    total += octroiGreenTAx

    println(total)

    println(quotation.subchargeType)
    println(charges.applicable)

    if (quotation.subchargeType == "Applicable") {

        subcharges = (quotation.subcharges.toDouble() / 100) * total
    }
    if (quotation.gstIn == charges.inQuotation) {

        gstin = (quotation.gst.toDouble() / 100) * total

    }
    if (quotation.insuranceType == charges.additional) {
        insuranceCharge = ((quotation.insuranceCharge.toDouble() / 100) * total)
        insuranceCharge += (insuranceCharge * (gstperc / 100))

    }
    if (quotation.insuranceType1 == charges.additional) {
        insuranceCharge1 = ((quotation.insuranceCharge1.toInt() / 100) * total)
        insuranceCharge1 += (insuranceCharge * (gstperc1 / 100))

    }

    // adding subtracting and making finalizing

    total += subcharges
    total += gstin
    total += insuranceCharge
    total += insuranceCharge1
    total -= advancePaid

// returning to the controller
    return QuotationOutput(
        id = quotation.id,
        status = quotation.status,
        markAsPaid = quotation.markAsPAid,
        partyNAme = quotation.partyName,
        from = quotation.city,
        to = quotation.city2,
        total = total.toString(),
        shiftingDate = quotation.shiftingDate,
        packagingDate = quotation.packingDate,
        gstin = gstin,
        insurance = insuranceCharge + insuranceCharge1,
        subcharges = subcharges
    )
}


fun BillCalculation(bill: Bill): TotalSubTotal {
    if (bill.freightCharge.isEmpty()) {
        bill.freightCharge = "0"
    }
    if (bill.advancePaid.isEmpty()) {
        bill.advancePaid = "0"
    }
    if (bill.packingCharge.isEmpty()) {
        bill.packingCharge = "0"
    }
    if (bill.otherCharge.isEmpty()) {
        bill.otherCharge = "0"
    }
    if (bill.unpackingCharge.isEmpty()) {
        bill.unpackingCharge = "0"
    }
    if (bill.loadingCharge.isEmpty()) {
        bill.loadingCharge = "0"
    }
    if (bill.unloadingCharge.isEmpty()) {
        bill.unloadingCharge = "0"
    }
    if (bill.packingMaterialCharge.isEmpty()) {
        bill.packingMaterialCharge = "0"
    }
    if (bill.StorageCharge.isEmpty()) {
        bill.StorageCharge = "0"
    }
    if (bill.carbikeTpt.isEmpty()) {
        bill.carbikeTpt = "0"
    }
    if (bill.miscellaneousCharge.isEmpty()) {
        bill.miscellaneousCharge = "0"
    }
    if (bill.stCharge.isEmpty()) {
        bill.stCharge = "0"
    }
    if (bill.greentax.isEmpty()) {
        bill.greentax = "0"
    }
    if (bill.discount.isEmpty()) {
        bill.discount = "0"
    }
    if (bill.gst.isEmpty()) {
        bill.gst = "18"
    }
    if (bill.insuranceCharge.isEmpty()) {
        bill.insuranceCharge = "0"
    }
    if (bill.insuranceGst.isEmpty()) {
        bill.insuranceGst = "18"
    }

    if (bill.vehicleInsuranceGst.isEmpty()) {
        bill.vehicleInsuranceGst = "18"
    }
    if (bill.vehicleInsuranceCharge.isEmpty()) {
        bill.vehicleInsuranceCharge = "0"
    }


    var freightCharge = bill.freightCharge.toDouble()
    var advancePaid = bill.advancePaid.toDouble()
    var packingCharge = bill.packingCharge.toDouble()
    var otherCharge = bill.otherCharge.toDouble()
    var unpackingCharge = bill.unpackingCharge.toDouble()
    var loadingCharge = bill.loadingCharge.toDouble()
    var unloadingCharge = bill.unloadingCharge.toDouble()
    var packageingMAterialCharge = bill.packingMaterialCharge.toDouble()
    var storagecharge = bill.StorageCharge.toDouble()
    var carbikeTpt = bill.carbikeTpt.toDouble()
    var miscleneousCharge = bill.miscellaneousCharge.toDouble()
    var stCharge = bill.stCharge.toDouble()
    var octroiGreenTAx = bill.greentax.toDouble()
    var discount = bill.discount.toDouble()
    var gstperc = bill.gst.toDouble()
    var insuranceCharge = bill.insuranceCharge.toDouble()
    var insuranceGst = bill.insuranceGst.toDouble()
    var vehicleInsuranceCharge = bill.vehicleInsuranceCharge.toDouble()
    var vehicleInsuranceGst = bill.vehicleInsuranceGst.toDouble()
    var total = 0.toDouble()
    var subTotal = 0.toDouble()
    subTotal =
        freightCharge + packingCharge + otherCharge + unpackingCharge + loadingCharge + unloadingCharge + packageingMAterialCharge + storagecharge + carbikeTpt + miscleneousCharge + stCharge + octroiGreenTAx


    val gstPerc = gstperc / 100 * subTotal

    val insCharge =  insuranceCharge + ((insuranceGst / 100)*insuranceCharge)
    total =
        (gstPerc) + subTotal + vehicleInsuranceCharge + ((vehicleInsuranceGst / 100) * vehicleInsuranceCharge)
    total -= discount+ insCharge -advancePaid

    return (TotalSubTotal(
                total,
                subTotal,
                gstPerc,
        insurancecharge =insCharge
        )
            )
}



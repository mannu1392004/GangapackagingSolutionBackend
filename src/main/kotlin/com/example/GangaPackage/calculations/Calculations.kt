package com.example.GangaPackage.calculations

import com.example.GangaPackage.models.Quotation
import com.example.GangaPackage.models.bill.Bill
import com.example.GangaPackage.models.bill.BillOtput
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
        date = quotation.date,
        gstin = gstin,
        insurance = insuranceCharge + insuranceCharge1,
        subcharges = subcharges
    )
}

fun BillCalculation(quotation: Bill): BillOtput {
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
    return BillOtput(
        billNo = quotation.id.toString(),
        status = quotation.marksStatus,
        billingDate = quotation.invoiceDate,
        BillingName = quotation.billToName,
        from = quotation.moveFrom,
        to =   quotation.moveTo,
        total = total.toString(),
        call = quotation.billToPhone
        )

}



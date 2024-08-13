package com.example.GangaPackage.calculations

import com.example.GangaPackage.models.lrbilty.LrBilty

data class TotalSubTotal(
    val total: Double,
    val subTotal: Double,
    val gstcal: Double,
    val insurancecharge : Double = 0.0
)


fun lrBillsCalculation(lrBilty: LrBilty): TotalSubTotal {
    if (lrBilty.totalBasicFreight.isEmpty()) {
        lrBilty.totalBasicFreight = "0"
    }
    if (lrBilty.freightToBeBilled.isEmpty()) {
        lrBilty.freightToBeBilled = "0"
    }
    if (lrBilty.otherCharges.isEmpty()) {
        lrBilty.otherCharges = "0"
    }
    if (lrBilty.loadingCharges.isEmpty()) {
        lrBilty.loadingCharges = "0"
    }
    if (lrBilty.unloadingCharges.isEmpty()) {
        lrBilty.unloadingCharges = "0"
    }
    if (lrBilty.stCharges.isEmpty()) {
        lrBilty.stCharges = "0"
    }
    if (lrBilty.lr_cnCharges.isEmpty()) {
        lrBilty.lr_cnCharges = "0"
    }
    if (lrBilty.gstperc.isEmpty()) {
        lrBilty.gstperc= "18"
    }

    val totalBasicFreight = lrBilty.totalBasicFreight.toDouble()
    val otherCharges = lrBilty.otherCharges.toDouble()
    val loadingCharges = lrBilty.loadingCharges.toDouble()
    val unloadingCharges = lrBilty.unloadingCharges.toDouble()
    val stCharges = lrBilty.stCharges.toDouble()
    val lr_cnCharges = lrBilty.lr_cnCharges.toDouble()
    val gstperc = lrBilty.gstperc.toDouble()


    val subTotal = totalBasicFreight + otherCharges + loadingCharges + unloadingCharges + stCharges + lr_cnCharges

    val gstcal = subTotal * gstperc / 100
    val total = subTotal + gstcal

    return TotalSubTotal(total, subTotal, gstcal)
}
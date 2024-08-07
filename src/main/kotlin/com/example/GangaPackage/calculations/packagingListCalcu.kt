package com.example.GangaPackage.calculations

import com.example.GangaPackage.models.packagingList.PackagingList

fun packageListCalc(packagingList: PackagingList): Int {
    val list = packagingList.particularList
    var total = 0
   if (!list.isEmpty()) {
       list.forEach {
           total += it.quantity.toInt() * it.value.toInt()
       }
   }
    return total
}
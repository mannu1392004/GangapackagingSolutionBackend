package com.example.GangaPackage.models.packagingList

data class PackagingList(
    var id: Int,
    var name: String = "",
    var phone: String = "",
    var packagingNumber: String = "",
    var date: String = "",
    var moveFrom: String = "",
    var moveTo: String = "",
    var vehicleNumber: String = "",
    var particularList: List<itemsParticulars>,
    var total: Int = 0
)

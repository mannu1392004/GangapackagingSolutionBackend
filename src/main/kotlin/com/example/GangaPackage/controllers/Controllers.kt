package com.example.GangaPackage.controllers

import com.example.GangaPackage.calculations.BillCalculation
import com.example.GangaPackage.calculations.QuotationCalculation
import com.example.GangaPackage.calculations.lrBillsCalculation
import com.example.GangaPackage.calculations.packageListCalc
import com.example.GangaPackage.models.Quotation
import com.example.GangaPackage.models.User
import com.example.GangaPackage.models.bill.Bill
import com.example.GangaPackage.models.bill.BillOtput
import com.example.GangaPackage.models.lrbilty.LrBilty
import com.example.GangaPackage.models.moneyReciept.MoneyReceipt
import com.example.GangaPackage.models.packagingList.PackagingList
import com.example.GangaPackage.models.quotation.QuotationOutput
import com.example.GangaPackage.pdfServices.*
import com.example.GangaPackage.services.UserServices
import com.example.GangaPackage.util.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.thymeleaf.TemplateEngine
import java.time.LocalDate
import java.util.UUID


@RestController
class Controllers(
    private val userServices: UserServices,
    private val jwtUtil: JwtUtil,
) {

    @Autowired
    private lateinit var templateEngine: TemplateEngine


    // get quotation form
    @GetMapping("/getQuotationForm/{jwtToken}")
    fun getQuotationForm(@PathVariable jwtToken: String): Quotation {
        println("This is working sending form")
        val username = jwtUtil.extractUsername(jwtToken)

        val user = userServices.findUserById(username)
        val quotationNo = user.totalQuotation + 1
        userServices.save(user)
        return Quotation(id = quotationNo, date = LocalDate.now().toString())
    }


    // save the quotation in the database and return the quotation id
    @PostMapping("/saveQuotation/{jwtToken}")
    fun saveQuotation(@PathVariable jwtToken: String, @RequestBody quotation: Quotation): String {
        println("This is working saving form")
        val username = jwtUtil.extractUsername(jwtToken)

        val user = userServices.findUserById(username)

        val cal = QuotationCalculation(quotation)
        quotation.total = cal.total.toString()
        user.quotationList += quotation
        user.totalQuotation += 1
        userServices.save(user)
        return "1"
    }


    // get the calculated quotation
    @GetMapping("/quotationList/{jwtToken}")
    fun CalculatedQuotation(@PathVariable jwtToken: String): List<Quotation> {
        println("hello this is working")
        val username = jwtUtil.extractUsername(jwtToken)
        println(username)
        val user = userServices.findUserById(username)
        return user.quotationList
    }


    // save the edited quotation in the database
    @PostMapping("/saveEditedQuotation/{id}/{jwtToken}")
    fun saveEditedQuotation(
        @PathVariable id: String,
        @PathVariable jwtToken: String,
        @RequestBody quotation: Quotation,
    ): String {


        println(quotation.list)
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        user.quotationList = user.quotationList.filter {
            it.id != quotation.id
        }

        val calculation = QuotationCalculation(quotation)
        quotation.total = calculation.total.toString()


        user.quotationList += quotation
        userServices.save(user)

        return "successful"
    }


    // getting the pdf of the quotation


    @GetMapping("/download/{id}/{jwtToken}")
    fun downloadPdf(@PathVariable id: String, @PathVariable jwtToken: String): ResponseEntity<ByteArray> {

        println("hello this is working pdf go for download")
        val id = id.toInt()
        val username = jwtUtil.extractUsername(jwtToken)

        // get the quotation from the database
        val user = userServices.findUserById(username)

        val quotation = user.quotationList.filter {
            it.id == id
        }

        val total = QuotationCalculation(quotation.get(0))

        val pdfBytes = QuotationPdfService().generatePdf(total, quotation.get(0), user)
        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quotation.pdf")
            add(HttpHeaders.CONTENT_TYPE, "application/pdf")
        }
        return ResponseEntity(pdfBytes, headers, HttpStatus.OK)
    }

    // deleting the quotation
    @GetMapping("/deleteQuote/{id}/{jwtToken}")
    fun deleteQuotation(@PathVariable id: String, @PathVariable jwtToken: String): String {
        val id = id.toInt()
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        user.quotationList = user.quotationList.filter {
            it.id != id
        }
        user.totalQuotation -= 1
        userServices.save(user)
        return "deleted"
    }


    // adding packaging List
    @PostMapping("/addPackagingList/{jwt}")
    fun addPackagingList(@PathVariable jwt: String, @RequestBody packagingList: PackagingList): String {
        println(packagingList.toString())


        val id = packagingList.id
        val username = jwtUtil.extractUsername(jwt)
        val user = userServices.findUserById(username)
        val x = user.packagingList.filter {
            it.id == id
        }

        if (
            x.isNotEmpty()
        ) {
            user.packagingList = user.packagingList.filter {
                it.id != id
            } + packagingList

        } else {

            packagingList.id = user.totalPackagingList + 1
            user.packagingList = user.packagingList.plus(packagingList)
            packagingList.total = packageListCalc(packagingList)
            user.totalPackagingList += 1
            println(user.totalPackagingList)

        }
        userServices.save(user)
        return "DoneBro"
    }

    // sending packaging list
    @GetMapping("/getPackagingList/{jwtToken}")
    fun getPackagingList(@PathVariable jwtToken: String): List<PackagingList> {
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        return user.packagingList
    }

    // deleting PackagingList
    @GetMapping("/deletePackagingList/{id}/{jwtToken}")
    fun deletePackagingList(@PathVariable id: String, @PathVariable jwtToken: String): String {
        val id = id.toInt()
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        user.packagingList = user.packagingList.filter {
            it.id != id
        }
        user.totalPackagingList -= 1
        userServices.save(user)
        return "deleted"
    }


    // lr bills
    @PostMapping("/addLrBill/{jwt}")
    fun addLrBill(@PathVariable jwt: String, @RequestBody bill: LrBilty): String {
        println(bill.toString())

        val calculation = lrBillsCalculation(bill)

        val id = bill.id
        val username = jwtUtil.extractUsername(jwt)
        val user = userServices.findUserById(username)
        val x = user.lrBills.filter {
            it.id == id
        }

        if (
            x.isNotEmpty()
        ) {
            bill.total = calculation.total.toString()
            bill.subtotal = calculation.subTotal.toString()
            bill.gstsubt = calculation.gstcal.toString()
            user.lrBills = user.lrBills.filter {
                it.id != id
            } + bill

        } else {
            bill.total = calculation.total.toString()
            bill.subtotal = calculation.subTotal.toString()
            bill.gstsubt = calculation.gstcal.toString()
            bill.id = user.totalLrBills + 1
            user.lrBills = user.lrBills.plus(bill)
            user.totalLrBills += 1
        }
        userServices.save(user)
        return "DoneBro"
    }

    // sending lr bills
    @GetMapping("/getLrBills/{jwtToken}")
    fun getLrBills(@PathVariable jwtToken: String): List<LrBilty> {
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        return user.lrBills
    }

    // deleting lr bills
    @GetMapping("/deleteLrBill/{id}/{jwtToken}")
    fun deleteLrBill(@PathVariable id: String, @PathVariable jwtToken: String): String {
        val id = id.toInt()
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        user.lrBills = user.lrBills.filter {
            it.id != id
        }
        user.totalLrBills -= 1
        userServices.save(user)
        return "deleted"
    }


    // money receipt
    @PostMapping("/addMoneyReceipt/{jwt}")
    fun addMoneyReceipt(@PathVariable jwt: String, @RequestBody bill: MoneyReceipt): String {
        println(bill.toString())

        val id = bill.id
        val username = jwtUtil.extractUsername(jwt)
        val user = userServices.findUserById(username)
        val x = user.moneyReceipt.filter {
            it.id == id
        }

        if (
            x.isNotEmpty()
        ) {
            user.moneyReceipt = user.moneyReceipt.filter {
                it.id != id
            } + bill

        } else {
            bill.id = user.totalMoneyReceipt + 1
            user.moneyReceipt = user.moneyReceipt.plus(bill)
            user.totalMoneyReceipt += 1
        }
        userServices.save(user)
        return "DoneBro"
    }

    // sending money receipt
    @GetMapping("/getMoneyReceipt/{jwtToken}")
    fun getMoneyReceipt(@PathVariable jwtToken: String): List<MoneyReceipt> {
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        return user.moneyReceipt
    }

    // deleting money receipt
    @GetMapping("/deleteMoneyReceipt/{id}/{jwtToken}")
    fun deleteMoneyReceipt(@PathVariable id: String, @PathVariable jwtToken: String): String {
        val id = id.toInt()
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        user.moneyReceipt = user.moneyReceipt.filter {
            it.id != id
        }
        user.totalMoneyReceipt -= 1
        userServices.save(user)
        return "deleted"
    }


    // bill
    @PostMapping("/addBill/{jwt}")
    fun addBill(@PathVariable jwt: String, @RequestBody bill: Bill): String {
        println(bill.toString())
val cal = BillCalculation(bill)
        bill.total = cal.total.toString()
        bill.subTotal = cal.subTotal.toString()

        val id = bill.id
        val username = jwtUtil.extractUsername(jwt)
        val user = userServices.findUserById(username)
        val x = user.bill.filter {
            it.id == id
        }

        if (
            x.isNotEmpty()
        ) {
            user.bill = user.bill.filter {
                it.id != id
            } + bill

        } else {

            bill.id = (user.totalBills + 1).toString()
            user.bill = user.bill.plus(bill)
            user.totalBills += 1
        }
        userServices.save(user)
        return "DoneBro"
    }

    // sending bill
    @GetMapping("/getBill/{jwtToken}")
    fun getBill(@PathVariable jwtToken: String): List<Bill> {
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        return user.bill
    }

    // deleting bill
    @GetMapping("/deleteBill/{id}/{jwtToken}")
    fun deleteBill(@PathVariable id: String, @PathVariable jwtToken: String): String {
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        user.bill = user.bill.filter {
            it.id != id.toString()
        }
        user.totalBills -= 1
        userServices.save(user)
        return "deleted"
    }





// test pdf

    @GetMapping("/testPackingList")
    fun testPdf(): ResponseEntity<ByteArray> {
        val pdfBytes = packingListPdf()
        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.pdf")
            add(HttpHeaders.CONTENT_TYPE, "application/pdf")
        }
        return ResponseEntity(pdfBytes, headers, HttpStatus.OK)
    }


    // test money receipt

    @GetMapping("/testMoneyReceipt")
    fun testMoneyReceipt(): ResponseEntity<ByteArray> {
        val pdfBytes = moneyReceiptPdf()
        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.pdf")
            add(HttpHeaders.CONTENT_TYPE, "application/pdf")
        }
        return ResponseEntity(pdfBytes, headers, HttpStatus.OK)
    }


    // test lr bills


    @GetMapping("/testLrBill")
    fun testLrBill(): ResponseEntity<ByteArray> {
        val pdfBytes = lrBillPdf()
        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.pdf")
            add(HttpHeaders.CONTENT_TYPE, "application/pdf")
        }
        return ResponseEntity(pdfBytes, headers, HttpStatus.OK)
    }


    // test bill


// login


}


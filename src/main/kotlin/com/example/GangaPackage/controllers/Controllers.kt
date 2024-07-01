package com.example.GangaPackage.controllers

import com.example.GangaPackage.calculations.QuotationCalculation
import com.example.GangaPackage.models.Quotation
import com.example.GangaPackage.models.bill.Bill
import com.example.GangaPackage.models.quotation.QuotationOutput
import com.example.GangaPackage.pdfServices.BillGenerationPdfService
import com.example.GangaPackage.pdfServices.QuotationPdfService
import com.example.GangaPackage.services.UserServices
import com.example.GangaPackage.util.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.thymeleaf.TemplateEngine
import java.time.LocalDate


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

        val username = jwtUtil.extractUsername(jwtToken)

        val user = userServices.findUserById(username)


        val size = user.quotationList?.size

        return Quotation(id = if (size != null) size + 1 else 1, date = LocalDate.now())
    }

    // save the quotation in the database and return the quotation id
    @PostMapping("/saveQuotation/{jwtToken}")
    fun saveQuotation(@PathVariable jwtToken: String, @RequestBody quotation: Quotation): Int {

        val username = jwtUtil.extractUsername(jwtToken)

        val user = userServices.findUserById(username)


        user.quotationList += quotation

        userServices.save(user)
        return quotation.id
    }

    // get the calculated quotation
    @GetMapping("/CalculatedQuotation/{id}/{jwtToken}")
    fun CalculatedQuotation(@PathVariable id: String, @PathVariable jwtToken: String): QuotationOutput {
        val id = id.toInt()
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        val quotation = user.quotationList?.get(id - 1)

        var total = QuotationOutput(
            id = null,
            date = null,
            total = null,
            status = null,
            markAsPaid = null,
            partyNAme = null,
            from = null,
            to = null,
            gstin = 2.3,
            insurance = 3.5,
            subcharges = null
        )
        if (quotation != null) {
            total = QuotationCalculation(quotation)
        }

        return total
    }


    @GetMapping("/download/{id}/{jwtToken}")
    fun downloadPdf(@PathVariable id: String, @PathVariable jwtToken: String): ResponseEntity<ByteArray> {
        val id = id.toInt()
        val username = jwtUtil.extractUsername(jwtToken)

        // get the quotation from the database
        val user = userServices.findUserById(username)

        val quotation = user.quotationList?.get(id - 1)!!

        val total = QuotationCalculation(quotation)

        val pdfBytes = QuotationPdfService().generatePdf(total, quotation, user)
        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quotation.pdf")
            add(HttpHeaders.CONTENT_TYPE, "application/pdf")
        }
        return ResponseEntity(pdfBytes, headers, HttpStatus.OK)
    }


    // get the raw bill details
    @GetMapping("/getBill/{jwtToken}")
    fun getBill(@PathVariable jwtToken: String): Bill {
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)

        val billNo = user.bill?.size
        return Bill(
            id = if (billNo != null) billNo + 1 else 1,
        )
    }

    // save the bill to the database
    @PostMapping("/saveBill/{jwtToken}")
    fun saveBill(@PathVariable jwtToken: String, @RequestBody bill: Bill): String {
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        user.bill += bill
        userServices.save(user)
        return "saved"
    }

    // get the calculated quotation
    @GetMapping("/CalculatedBill/{id}/{jwtToken}")
    fun CalculatedBill(@PathVariable id: String, @PathVariable jwtToken: String): Bill {
        val id = id.toInt()
        val username = jwtUtil.extractUsername(jwtToken)
        val user = userServices.findUserById(username)
        val bill = user.bill?.get(id - 1)

        return bill!!
    }

    // getting the calculated bill
    @GetMapping("/getPdf")
    fun getPdf(): ResponseEntity<ByteArray> {
        val pdfBytes = BillGenerationPdfService()
        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quotation.pdf")
            add(HttpHeaders.CONTENT_TYPE, "application/pdf")
        }
        return  ResponseEntity(pdfBytes, headers, HttpStatus.OK)
    }
}
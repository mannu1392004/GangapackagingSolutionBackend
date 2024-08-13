package com.example.GangaPackage.pdfServices

import com.example.GangaPackage.models.User
import com.example.GangaPackage.models.lrbilty.LrBilty
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.element.*
import com.itextpdf.layout.properties.HorizontalAlignment
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.VerticalAlignment
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URL
import javax.imageio.ImageIO

fun lrBillPdf(user: User,lrBilty: LrBilty):ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val writer = PdfWriter(byteArrayOutputStream)
    val pdfDocument = PdfDocument(writer)
    pdfDocument.defaultPageSize = PageSize.A3
    val document = Document(pdfDocument)
    val color = DeviceRgb(17, 17, 32)


    val headerTable = Table(2).useAllAvailableWidth().setBorder(Border.NO_BORDER)


// Step 1: Download the image from the URL
    val imageUrl = URL(user.profile) // Replace with your image URL
    val bufferedImage: BufferedImage = ImageIO.read(imageUrl)

// Step 2: Convert the image to PNG format
    val pngFilePath = "converted_image.png"
    ImageIO.write(bufferedImage, "png", File(pngFilePath))

// Step 3: Load the PNG image using iText
    val imageData = ImageDataFactory.create(pngFilePath)
    val image = Image(imageData).scaleToFit(100f, 100f).setHorizontalAlignment(HorizontalAlignment.CENTER).setMarginTop(20f)// Adjust image size as needed


// Create header paragraph
    val header = Paragraph() // Align text to the right
        .setVerticalAlignment(VerticalAlignment.MIDDLE)
        .setTextAlignment(TextAlignment.RIGHT) // Align text vertically in the middle

// Add text elements to the header paragraph
    header.add(Paragraph(user.companyName).setFontSize(20f).setMargin(0f).setFontColor(color))
    header.add("\n")
    header.add("\nEmail: ${user.gmail}\n").setFontSize(12f)
    header.add("Contact: ${user.mobileNumber}\n").setFontSize(12f)

// Add image and header to the table without borders
    headerTable.addCell(image.setBorder(Border.NO_BORDER))
    headerTable.addCell(header.setBorder(Border.NO_BORDER))

// Add the header table to the document
    document.add(headerTable)


    // Title
    val title = Paragraph("LR (LORRY RECEIPT / Consignment Note)").apply {
        setBackgroundColor(color)
        setTextAlignment(TextAlignment.CENTER)
        setMargin(0f)
        setFontSize(18f)
        setFontColor(DeviceRgb(255, 255, 255))
    }
    document.add(title)


    val table = Table(3).useAllAvailableWidth()
    table.addCell("Notice\n" +
            "The consignment by the lorry receipt shall be stored at the destination under the control of the transport operator and shall be delivered to the order of the consignee whose name is mentioned in the lorry receipt. It will under no circumstance be delivered to anyone without written authorization from the consignee on its order")


    table.addCell(Paragraph("AT OWNER'S RISK").setFontSize(18f).setBold().setFontColor(color).setTextAlignment(TextAlignment.CENTER).setMargin(20f))

    val paragraph = Paragraph().apply {
        setMargin(0f)
        add("Lr No: ${lrBilty.lrNumber}\n")
        add("Lr Date: ${lrBilty.lrDate}\n")
        add("Move From: ${lrBilty.moveFrom}\n")
        add("Move To: ${lrBilty.moveTo}\n")
        add("Vehicle No.: ${lrBilty.truckNumber}\n")
    }.setBold()
        .setMargin(20f)


    table.addCell(paragraph)


    table.addCell(Paragraph("Move From").apply {
        setBackgroundColor(color)
        setTextAlignment(TextAlignment.LEFT)
        setMargin(0f)
        setFontSize(13f)
        setFontColor(DeviceRgb(255, 255, 255))
    })

    table.addCell(Cell(1,2).add(Paragraph("Move To").apply {
        setBackgroundColor(color)
        setTextAlignment(TextAlignment.LEFT)
        setMargin(0f)
        setFontSize(13f)
        setFontColor(DeviceRgb(255, 255, 255))
    }))



    table.addCell(Paragraph().apply {
        add("Name: ")
        add("${lrBilty.consignorName}\n").setBold()
        add("Mobile No. ")
        add("${lrBilty.consignorNumber}\n").setBold()
        add("Gst No. ")
        add("${lrBilty.gstNumber}\n").setBold()
        add("Address: ")
        add("${lrBilty.address}\n").setBold()
        add("State/city/pin: ")
        add("${lrBilty.state}/${lrBilty.city}/${lrBilty.pincode}\n").setBold()
    })

    table.addCell(
        Cell(1,2).add(
        Paragraph().apply {
        add("Name: ")
        add("${lrBilty.consigneeName}\n").setBold()
        add("Mobile No. ")
        add("${lrBilty.consigneeNumber}\n").setBold()
        add("Gst No. ")
        add("${lrBilty.gstNumber1}\n").setBold()
        add("Address: ")
        add("${lrBilty.address1}\n").setBold()
        add("State/city/pin: ")
        add("${lrBilty.state}/${lrBilty.city}/${lrBilty.pincode}\n").setBold()
    }))


    val fix = Paragraph("\n${lrBilty.numberOfPackages}").setBold()
    val parax = Paragraph("Number Of Package\n").add(fix)

    table.addCell(parax)

    val totalWeightsValue = Paragraph("\n${lrBilty.chargedWeight}/${lrBilty.chargedWeightType}").setBold()
    val totalWeights = Paragraph("Total Weight/Volume\n").add(totalWeightsValue)


    table.addCell(totalWeights)

    val condition = Paragraph("\n${lrBilty.receivePackageCondition}").setBold()
    val packageCondition =Paragraph("Receive Package Condition").add(condition)


    table.addCell(packageCondition)


    val freightPaid = Paragraph(lrBilty.freightPaid).setBold()
    val freight = Paragraph("Freight Paid\n").add(freightPaid).setFontColor(DeviceRgb(255, 255, 255)).setBackgroundColor(color).setTextAlignment(TextAlignment.CENTER).setBold()

    table.addCell(freight)

    val freightToPayValue = Paragraph(lrBilty.freightBalance).setBold()
    val freightToPay = Paragraph("Freight To Pay\n").add(freightToPayValue).setFontColor(DeviceRgb(255, 255, 255)).setBackgroundColor(color).setTextAlignment(TextAlignment.CENTER).setBold()

    table.addCell(freightToPay)

    val freightToBeBilledValue = Paragraph(lrBilty.freightToBeBilled)
    val freightToBeBilled = Paragraph("Freight To Be Billed\n").add(freightToBeBilledValue).setFontColor(DeviceRgb(255, 255, 255)).setBackgroundColor(color).setTextAlignment(TextAlignment.CENTER).setBold()
    table.addCell(freightToBeBilled)


    table.addCell(Cell(1,3).add(Paragraph("Remark:${lrBilty.remarks}")))

    table.addCell(Cell(1, 3).add(Paragraph("Packages and Goods Description:${lrBilty.description}")))
    table.addCell(Cell(1, 3).add(Paragraph("Schedule of demurrage charge:Demurrage charge after More Than ${lrBilty.demurageChargeApplicableAfter} @${lrBilty.demarrageCharge} Rupee + handling charges & local transportation charges.")))



    table.addCell(Cell(1,3).add(Paragraph().apply {
        add("Material Insurance : ${lrBilty.materialInsurance}")
            .add("Insurance Company :${lrBilty.insuranceCompany}")
            .add("Insurance Covered: ${lrBilty.insuredAmount}")
            .add("Insurance Date : ${lrBilty.insuranceDate}")
    }))

    table.addCell(
        imageDownload(user.signature)
    )
    table.addCell(
        Cell(1,2).add(Paragraph("I Agree with Terms & Conditions as\n" +
                "Overleaf Signature Receiver's").setHorizontalAlignment(HorizontalAlignment.CENTER)
    ))

    table.addCell(Cell(1,3).add(Paragraph("Note:\n" +
            "Please keep your Cash/Jewellery and anyway in your Custody/Lock\" Carring Liquor, Gas Cylinder, Acid of any type of Liquids (like Ghee\n" +
            "Tin, Oil etc.) is totally prohibited")))
    table.addCell(Cell(1, 3).add(Paragraph("For any queries, please contact us at 7015932229").setBold().setHorizontalAlignment(HorizontalAlignment.CENTER)))



    document.add(table)

















    document.close()
    return byteArrayOutputStream.toByteArray()
}

fun imageDownload(url:String):Image{
    val imageUrl = URL(url) // Replace with your image URL
    val bufferedImage: BufferedImage = ImageIO.read(imageUrl)

// Step 2: Convert the image to PNG format
    val pngFilePath = "converted_image.png"
    ImageIO.write(bufferedImage, "png", File(pngFilePath))

// Step 3: Load the PNG image using iText
    val imageData = ImageDataFactory.create(pngFilePath)
    val image = Image(imageData).scaleToFit(200f, 200f).setHorizontalAlignment(HorizontalAlignment.CENTER).setMarginTop(20f)// Adjust image size as needed

    return image
}


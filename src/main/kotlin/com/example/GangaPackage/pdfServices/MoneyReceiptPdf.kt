package com.example.GangaPackage.pdfServices

import com.example.GangaPackage.models.User
import com.itextpdf.html2pdf.html.AttributeConstants.URL
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.*
import com.itextpdf.styledxmlparser.css.CommonCssConstants.URL
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URL
import javax.imageio.ImageIO


fun moneyReceiptPdf(user: User):ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val writer = PdfWriter(byteArrayOutputStream)
    val pdfDocument = PdfDocument(writer)
    pdfDocument.defaultPageSize = PageSize.A3
    val document = Document(pdfDocument)
    val color = DeviceRgb(0, 0, 255)

    val headerTable = Table(2).useAllAvailableWidth().setBorder(Border.NO_BORDER)

// Step 1: Download the image from the URL
    val imageUrl = URL(user.qrCode) // Replace with your image URL
    val bufferedImage: BufferedImage = ImageIO.read(imageUrl)

// Step 2: Convert the image to PNG format
    val pngFilePath = "converted_image.png"
    ImageIO.write(bufferedImage, "png", File(pngFilePath))

// Step 3: Load the PNG image using iText
    val imageData = ImageDataFactory.create(pngFilePath)
    val image = Image(imageData).scaleToFit(100f, 100f).setMarginTop(20f) // Adjust image size as needed

// Create header paragraph
    val header = Paragraph() // Align text to the right
        .setVerticalAlignment(VerticalAlignment.MIDDLE)
        .setTextAlignment(TextAlignment.RIGHT) // Align text vertically in the middle

// Add text elements to the header paragraph
    header.add(Paragraph("Mahavir Truck").setFontSize(20f).setMargin(0f).setFontColor(color))
    header.add("\n")
    header.add("\nEmail: mannu1392004@gmail.com\n").setFontSize(12f)
    header.add("Contact: 7015932229\n").setFontSize(12f)
    header.add("\nBranch:").setFontSize(12f)

// Add image and header to the table without borders
    headerTable.addCell(image.setBorder(Border.NO_BORDER))
    headerTable.addCell(header.setBorder(Border.NO_BORDER))

// Add the header table to the document
    document.add(headerTable)

    val title = Paragraph("Money Receipt").apply {
        setBackgroundColor(color)
        setTextAlignment(TextAlignment.CENTER)
        setMargin(0f)
        setFontSize(18f)
        setFontColor(DeviceRgb(255, 255, 255))
    }
    document.add(title)

// Create a table with 2 columns
    val table = Table(UnitValue.createPercentArray(2)).useAllAvailableWidth()

    // Add header row
    table.addCell(Cell(1, 2).add(Paragraph("Receipt No.: 0001").setBold()).setTextAlignment(TextAlignment.LEFT))
    table.addCell(Cell(1, 2).add(Paragraph("Date: 20-07-2024").setBold()).setTextAlignment(TextAlignment.RIGHT))

    // Add thank you note
    table.addCell(Cell(1, 2).add(Paragraph("Received with thanks from M/s. Mannu").setBold()).setTextAlignment(TextAlignment.CENTER))

    // Add quotation number
    table.addCell(Cell(1, 2).add(Paragraph("Towards Part Payment of Quotation No.: 1").setBold()).setTextAlignment(TextAlignment.LEFT))

    // Add from/to details
    table.addCell(Paragraph("From").setBold()).setTextAlignment(TextAlignment.LEFT)
    table.addCell(Paragraph("To").setBold()).setTextAlignment(TextAlignment.LEFT)

    // Add cash details
    table.addCell(Paragraph("as per details by : CASH").setBold()).setTextAlignment(TextAlignment.LEFT)
    table.addCell(Paragraph("No.").setBold()).setTextAlignment(TextAlignment.RIGHT)

    val title2 = Paragraph("Rs. 10000 Only").apply {
        setBackgroundColor(color)
        setTextAlignment(TextAlignment.CENTER)
        setMargin(0f)
        setFontSize(18f)
        setFontColor(DeviceRgb(255, 255, 255))
    }
    // Add amount in words
    table.addCell(Cell(1, 2).add(title2))

    // Add phone number and date
    table.addCell(Cell(1, 2).add(Paragraph("Phone No.: 7015932229").setBold()).setTextAlignment(TextAlignment.RIGHT))


    val imageUrl1 =user.signature // Replace with your image URL
    val imageData1 = ImageDataFactory.create(URL(imageUrl1))
    val image1 = Image(imageData1).scaleToFit(100f, 100f) //

val para = Paragraph("Signature:\n").setBold().setTextAlignment(TextAlignment.RIGHT).setTextAlignment(TextAlignment.RIGHT) // Align text to the right
    .setVerticalAlignment(VerticalAlignment.MIDDLE) // Align text vertically in the middle
    .setBorder(SolidBorder(1f)).setPadding(10f).setMargin(0f)
    para.add(image1)











    // Add table to document
   document.add(table)

    document.add(para)





    document.close()
    return byteArrayOutputStream.toByteArray()

}
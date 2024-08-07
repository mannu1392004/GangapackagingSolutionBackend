package com.example.GangaPackage.pdfServices

import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import java.io.ByteArrayOutputStream

fun moneyReceiptPdf():ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val writer = PdfWriter(byteArrayOutputStream)
    val pdfDocument = PdfDocument(writer)
    pdfDocument.defaultPageSize = PageSize.A3
    val document = Document(pdfDocument)
    val color = DeviceRgb(0, 0, 255)



    // header
    val header = Paragraph().setTextAlignment(TextAlignment.RIGHT) // Align text to the right
        .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE) // Align text vertically in the middle
        .setBorder(com.itextpdf.layout.borders.SolidBorder(1f)).setPadding(10f).setMargin(0f)
    // Add elements to the header paragraph
    header.add(
        Paragraph("Mahavir Truck").setFontSize(20f).setMargin(0f).setFontColor(color)
    )// Use \n for new line
    header.add("\n")
    header.add("\nEmail: mannu1392004@gmail.com\n").setFontSize(12f)
    header.add("Contact: 7015932229\n").setFontSize(12f)
    header.add("\nBranch:").setFontSize(12f)

    document.add(header)

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

    table.addCell(Cell(1, 2).add(Paragraph("Signature").setBold().setHeight(100f)).setTextAlignment(TextAlignment.RIGHT))





    // Add table to document
   document.add(table)







    document.close()
    return byteArrayOutputStream.toByteArray()

}
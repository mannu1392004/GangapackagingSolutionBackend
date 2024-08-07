package com.example.GangaPackage.pdfServices

import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import java.io.ByteArrayOutputStream

fun lrBillPdf():ByteArray {
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
    header.add("\nEmail: mannu1392004@gmail.com\n").setFontSize(12f)
    header.add("Contact: 7015932229").setFontSize(12f)

    document.add(header)






    document.close()
    return byteArrayOutputStream.toByteArray()
}
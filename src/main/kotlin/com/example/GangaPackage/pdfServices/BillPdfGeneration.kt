package com.example.GangaPackage.pdfServices

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
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.VerticalAlignment
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

fun BillGenerationPdfService(): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val writer = PdfWriter(byteArrayOutputStream)
    val pdfDocument = PdfDocument(writer)
    pdfDocument.defaultPageSize = PageSize.A3
    val document = Document(pdfDocument)
    val color = DeviceRgb(0, 0, 255)

    // Header
    val header = Paragraph().setTextAlignment(TextAlignment.RIGHT) // Align text to the right
        .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE) // Align text vertically in the middle
        .setBorder(com.itextpdf.layout.borders.SolidBorder(1f)).setPadding(10f).setMargin(0f)
    // Add elements to the header paragraph
    header.add(
        Paragraph("Mahavir Truck").setFontSize(20f).setMargin(0f).setFontColor(DeviceRgb(0, 128, 0))
    )// Use \n for new line
    header.add("\nEmail: mannu1392004@gmail.com\n").setFontSize(12f)
    header.add("Contact: 7015932229").setFontSize(12f)

    document.add(header)

    // Title
    val title = Paragraph("Bill(Tax Invoice)").apply {
        setBackgroundColor(color)
        setTextAlignment(TextAlignment.CENTER)
        setMargin(0f)
        setFontSize(18f)
        setFontColor(DeviceRgb(255, 255, 255))
    }
    document.add(title)

    // Table for the bill details
    val table = Table(floatArrayOf(1f, 2f)).useAllAvailableWidth()

    // Left side of the table
    val leftTable = Table(1).useAllAvailableWidth()

    // First table with bill details
    val firstTable = Table(2).useAllAvailableWidth()

    val paragraph1 = Paragraph().apply {
        setMargin(0f)
        add("Bill No: 1\n")
        add("Billing Date: 12-06-2024\n")
        add("Lr.No.: hr-24-244\n")
        add("Delivery Date: 12-06-2024\n")
        add("Vehicle No.: HR-20s-332\n")
    }
    firstTable.addCell(paragraph1)

    val paragraph2 = Paragraph().apply {
        setMargin(2f)
        add("Moving Path: By Road\n")
        add("Moving Path Remark: By Road\n")
        add("Type of Shipment: Household Goods\n")
        add("From: Sonipat\n")
        add("To: Noida\n")
    }
    firstTable.addCell(paragraph2)





    leftTable.addCell(firstTable)



    leftTable.addCell(
        Paragraph("Bill To").setBackgroundColor(color).setFontColor(DeviceRgb(255, 255, 255))
            .setMargin(0f).setPaddingLeft(2f)
    )

    val paragraph3 = Paragraph()
    paragraph3.add("Company Name :Shri Nath\n")
    paragraph3.add("Name: Ramnath\n")
    paragraph3.add("Phone: 7015932229\n")
    paragraph3.add("Gst No.: \n")
    paragraph3.add("Country: India\n")
    paragraph3.add("Adress: Vikash colony sonipat\n")
    paragraph3.add("City/State/PinCode: N/A/Haryana/131001\n")

    leftTable.addCell(paragraph3)


    // Second table with bill to details
    val secondTable = Table(2).useAllAvailableWidth()
    secondTable.addHeaderCell(
        Paragraph("Move From").setBackgroundColor(color).setFontColor(DeviceRgb(255, 255, 255))
            .setMargin(0f).setPaddingLeft(2f)
    )

    secondTable.addHeaderCell(Paragraph("Move To").setBackgroundColor(color).setFontColor(DeviceRgb(255, 255, 255)))

    val paragraph4 = Paragraph()

    paragraph4.add("Name: Ramnath")
    paragraph4.add("Phone: 7015932229\n")
    paragraph4.add("Gst No.: \n")
    paragraph4.add("Country: India\n")
    paragraph4.add("Adress: Vikash colony sonipat\n")
    paragraph4.add("City/State/PinCode: N/A/Haryana/131001\n")

    secondTable.addCell(paragraph4)

    val paragraph5 = Paragraph()

    paragraph5.add("Name: Mannu")
    paragraph5.add("Phone: 7015932229\n")
    paragraph5.add("Gst No.: \n")
    paragraph5.add("Country: India\n")
    paragraph5.add("Adress: Vikash colony sonipat\n")
    paragraph5.add("City/State/PinCode: N/A/Haryana/131001\n")

    secondTable.addCell(paragraph5)


    val paragraph6 = Paragraph()
    paragraph6.add("Package: \n")
    paragraph6.add("N/A")

    secondTable.addCell(paragraph6)

    val paragraph7 = Paragraph()
    paragraph7.add("Packages and Goods Description: \n")
    paragraph7.add("N/A")
    secondTable.addCell(paragraph7)

    val paragraph8 = Paragraph()
    paragraph8.add("Total Weight/Volume: \n")
    paragraph8.add("N/A")
    secondTable.addCell(paragraph8)

    val paragraph9 = Paragraph()
    paragraph9.add("Payment Remark: \n")
    paragraph9.add("N/A")
    secondTable.addCell(paragraph9)

    val paragraph10 = Paragraph()
    paragraph10.add("Remark: \n")
    paragraph10.add("N/A")
    val span = Cell(1,2).add(paragraph10)
    secondTable.addCell(span)

    val paragraph11 = Paragraph()
    paragraph11.add("Insurance charge @3% on declaration value of goods").setBold().setFontSize(8f)
    val span2 = Cell(1, 2).add(paragraph11)
    secondTable.addCell(span2)

    val paragraph12 = Paragraph()
    paragraph12.add("Note").setBold().setFontSize(10f)
    paragraph12.add("\n1 Please Keep yore Cash/jewellery and anyway in your Custody/Lock Carrying Liquor,Gas,Cylinder,Acid of any type of Liquids is totally prohibited. ")
    val span3 = Cell(1, 2).add(paragraph12)
    secondTable.addCell(span3)



    // sign and term condition
    val image = drawStamp("Mannu")
    // Convert BufferedImage to byte array
    val baos = ByteArrayOutputStream()
    ImageIO.write(image, "png", baos)
    val imageData = ImageDataFactory.create(baos.toByteArray())
    val pdfimage = Image(imageData)
    pdfimage.setHeight(150f)
    pdfimage.setWidth(150f)

    val stampText = "Stamp"
    val fontSize = 10f


    val stampParagraph = Paragraph(stampText)
        .setFontColor(color)
        .setFontSize(fontSize)
        .setTextAlignment(TextAlignment.CENTER)

    val stampCell = Cell().add(stampParagraph)
    secondTable.addCell(pdfimage)

    // agree cell
    secondTable.addCell(
        Paragraph("I Agree with Terms & Condition as Overleaf Signature Receiver's").setTextAlignment(
            TextAlignment.LEFT
        ).setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(6f)
    )




    leftTable.addCell(secondTable)

    table.addCell(leftTable)



    // Right side of the table

    val paymentTable = Table(2)
        .useAllAvailableWidth()
        .setBorder(Border.NO_BORDER)
        .setFontSize(10f)

    paymentTable.addHeaderCell(
        Cell().add(
            Paragraph("Particulars").setPaddingRight(5f).setPaddingLeft(5f).setBackgroundColor(color)
                .setFontColor(DeviceRgb(255, 255, 255))
        )
    )
    paymentTable.addHeaderCell(
        Cell().add(
            Paragraph("Amount(Rs.)").setPaddingRight(5f).setPaddingLeft(5f).setBackgroundColor(color)
                .setFontColor(DeviceRgb(255, 255, 255))
        )
    )

    paymentTable.addCell("Freight")
    paymentTable.addCell("2000")
    paymentTable.addCell("Packing Staff charge")
    paymentTable.addCell("1000")
    paymentTable.addCell("Un Packing Staff Charge")
    paymentTable.addCell("500")
    paymentTable.addCell("Loading Charge")
    paymentTable.addCell("1000")
    paymentTable.addCell("Un Loading Charge")
    paymentTable.addCell("1000")
    paymentTable.addCell("Pack. Material Charge")
    paymentTable.addCell("100")
    paymentTable.addCell("Other Charge")
    paymentTable.addCell("200")
    paymentTable.addCell("S.T Charge")
    paymentTable.addCell("100")
    paymentTable.addCell("Discount")
    paymentTable.addCell("600")

    paymentTable.addCell(
        Cell().add(
            Paragraph("Sub Total").setPaddingRight(5f).setPaddingLeft(5f).setBackgroundColor(color)
                .setFontColor(DeviceRgb(255, 255, 255))
        )
    )
    paymentTable.addCell(
        Cell().add(
            Paragraph("1000").setPaddingRight(5f).setPaddingLeft(5f).setBackgroundColor(color)
                .setFontColor(DeviceRgb(255, 255, 255))
        )
    )

    paymentTable.addCell("SGST (9%)")
    paymentTable.addCell("2108")
    paymentTable.addCell("CGST (9%)")
    paymentTable.addCell("2108")
    paymentTable.addCell("Insurance Charge")
    paymentTable.addCell("Optional")
    paymentTable.addCell("SubCharges")
    paymentTable.addCell("2342")

    paymentTable.addCell(
        Cell().add(
            Paragraph("Total Amount").setPaddingRight(5f).setPaddingLeft(5f)
                .setBackgroundColor(color)
                .setFontColor(DeviceRgb(255, 255, 255))
        )
    )
    paymentTable.addCell(
        Cell().add(
            Paragraph("29983").setPaddingRight(5f).setPaddingLeft(5f)
                .setBackgroundColor(color)
                .setFontColor(DeviceRgb(255, 255, 255))
        )
    )

    val paragraph13 = Paragraph()
    paragraph13.add("Gst Paid By: Consignee \n")
    paragraph13.add("Reverse Charge : No\n")
    paragraph13.add("\n")
    paragraph13.add("\n")
    paragraph13.add("\n")
    paragraph13.add("\n")
    paragraph13.add("\n")
    paragraph13.add("\n")

    val span4 = Cell(1,2).add(paragraph13)
    paymentTable.addCell(span4)

    val paragraph14 = Paragraph()
    paragraph14.add("Bank Details-\n").setBold().setFontColor(color)
    paragraph14.add("Bank Name:\n")
    paragraph14.add("Bank A/c No:\n")
    paragraph14.add("IFSC Code:\n")
    paragraph14.add("Other Payment Options:\n").setBold().setFontColor(color)
    paragraph14.add("UPI Id:\n")

val span5 = Cell(1, 2).add(paragraph14)
    paymentTable.addCell(span5)



    table.addCell(Cell().add(paymentTable))


    val papergenerated = Paragraph().setFontSize(12f).setPaddingLeft(10f)

    papergenerated.add("This is a computer generated document ").setBold()
    papergenerated.add(Paragraph("Save Paper Save Trees | Be digital Go green").setFontColor(color))

    val cell2 = Cell(1, 2).add(papergenerated)
    table.addCell(cell2)



    document.add(table)

    val querypara = Paragraph().setTextAlignment(TextAlignment.CENTER) // Align text to the right
        .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE) // Align text vertically in the middle
        .setBorder(com.itextpdf.layout.borders.SolidBorder(1f)).setMargin(0f)
    querypara.add("For any Query Contact us:")
    querypara.add(Paragraph("7015932229").setBold())


    document.add(querypara)


    document.close()
    return byteArrayOutputStream.toByteArray()
}

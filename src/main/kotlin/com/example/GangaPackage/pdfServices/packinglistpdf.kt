package com.example.GangaPackage.pdfServices

import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.VerticalAlignment
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

fun packingListPdf(): ByteArray {
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

    val title = Paragraph("Packing List/Receipt").apply {
        setBackgroundColor(color)
        setTextAlignment(TextAlignment.CENTER)
        setMargin(0f)
        setFontSize(18f)
        setFontColor(DeviceRgb(255, 255, 255))
    }
    document.add(title)


    // making A table
    val table = com.itextpdf.layout.element.Table(4).useAllAvailableWidth()
    table.addCell("Date: 27/09/2023")
    table.addCell("Packing No.: 1")
    table.addCell("Name: Mannu")
    table.addCell("Phone: 7015932229")




    table.addCell("Move From : Charkhi Dadri")
    table.addCell("Move To : Charkhi Dadri")
    val par = Paragraph("Vehicle No.: MH-12-AB-1234")
    val span = Cell(1, 2).add(par)
    table.addCell(span)
    document.add(table)

    // next title
    val title2 = Paragraph("Moving Item / Particulars Details").apply {
        setBackgroundColor(color)
        setTextAlignment(TextAlignment.CENTER)
        setMargin(0f)
        setFontSize(18f)
        setFontColor(DeviceRgb(255, 255, 255))
    }
    document.add(title2)

//    making A table
    val table2 = com.itextpdf.layout.element.Table(5).useAllAvailableWidth()
    table2.addCell("S.No.")
    table2.addCell("Goods Description")
    table2.addCell("Quantity")
    table2.addCell("Value")
    table2.addCell("Remark")



    table2.addCell("1")
    table2.addCell("Chairs")
    table2.addCell("10")
    table2.addCell("2000")
    table2.addCell("Good Conditions")


    val span1 = Cell(1, 2).add(Paragraph("    "))
    table2.addCell(span1)


    table2.addCell("Total : 10")
    table2.addCell("Total :2000")

    table2.addCell("    ")


    val span2 = Cell(1, 3)
    // sign and term condition
    val image = drawStamp("user.companyName")
    // Convert BufferedImage to byte array
    val baos = ByteArrayOutputStream()
    ImageIO.write(image, "png", baos)
    val imageData = ImageDataFactory.create(baos.toByteArray())
    val pdfimage = Image(imageData)
    pdfimage.setHeight(150f)
    pdfimage.setWidth(150f)


    span2.add(pdfimage)
   table2.addCell(span2)
    val span3 = Cell(1, 2)
    span3.add(
        Paragraph("I Agree with Terms & Condition as Overleaf Signature Receiver's").setTextAlignment(
            TextAlignment.LEFT
        ).setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(9f)
    )

    table2.addCell(span3)
    document.add(table2)

    // title 3
    val title3 = Paragraph(" We have checked all items listed and numbered 01-01 inclusive with a total number of 01 and acknowledge that this is a true and complete listing of the goods presented and the condition of the goods is subject to change.").apply {
        setBackgroundColor(color)
        setMargin(0f)
        setFontSize(13f)
        setFontColor(DeviceRgb(255, 255, 255))
    }
    document.add(title3)

        // table 4
    val table4 = com.itextpdf.layout.element.Table(1).useAllAvailableWidth()

   val paragraph = Paragraph("Note:\n" +
           "Please keep your Cash/Jewellery and anyway in your Custody/Lock\" Carring Liquor, Gas Cylinder, Acid of any type of Liquids (like Ghee\n" +
           "Tin, Oil etc.) is totally prohibited.").apply {
               setMargin(0f)
               setFontSize(12f)
    
       setTextAlignment(TextAlignment.LEFT)
       setBold()
           }

    table4.addCell(paragraph)

    val gogreenpara=Paragraph("This is a computer-generated document signature not required. SAVE PAPER - SAVE TREES | BE DIGITAL - GO GREEN").apply {
        setTextAlignment(TextAlignment.CENTER)
    }
    table4.addCell(gogreenpara)
    table4.addCell("For Any Query contact us: Mob.: 8683088353").setTextAlignment(TextAlignment.CENTER)



    document.add(table4)



    document.close()
    return byteArrayOutputStream.toByteArray()
}
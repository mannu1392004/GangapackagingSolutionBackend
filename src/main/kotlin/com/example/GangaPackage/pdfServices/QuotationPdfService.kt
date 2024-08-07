package com.example.GangaPackage.pdfServices

import com.example.GangaPackage.models.Quotation
import com.example.GangaPackage.models.User
import com.example.GangaPackage.models.quotation.QuotationOutput
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.HorizontalAlignment
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import com.itextpdf.layout.properties.VerticalAlignment
import org.springframework.stereotype.Service
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.geom.AffineTransform
import java.awt.geom.RoundRectangle2D
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Service
class QuotationPdfService {

    fun generatePdf(quotationOutput: QuotationOutput, quotation: Quotation, user: User): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val writer = PdfWriter(byteArrayOutputStream)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument, PageSize.A4)

        val header = Paragraph().setTextAlignment(TextAlignment.RIGHT) // Align text to the right
            .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE) // Align text vertically in the middle
            .setBorder(com.itextpdf.layout.borders.SolidBorder(1f)).setPadding(10f).setMargin(0f)
        // Add elements to the header paragraph
        header.add(
            Paragraph(user.companyName).setFontSize(20f).setMargin(0f).setFontColor(DeviceRgb(0, 128, 0))
        )// Use \n for new line
        header.add("\nEmail: ${user.gmail}\n").setFontSize(12f)
        header.add("Contact: ${user.id}").setFontSize(12f)

        document.add(header)

        // setting the
        val title =
            Paragraph().setBackgroundColor(DeviceRgb(0, 128, 0)).setTextAlignment(TextAlignment.CENTER).setMargin(0f)

        title.add("Quotation").setFontSize(18f).setFontColor(DeviceRgb(255, 255, 255))
        document.add(title)

        // creating the table
        val table = Table(floatArrayOf(1f, 2f)).useAllAvailableWidth()

        val paragraph1 = Paragraph().setMargin(0f)
        paragraph1.add("Company Name :${quotation.companyName}\n")
        paragraph1.add("Name: ${quotation.partyName}\n")
        paragraph1.add("Email: ${quotation.email}\n")
        paragraph1.add("Phone: ${quotation.phoneNumber}\n")
        paragraph1.add("Moving Type: ${quotation.movingType}\n")

        table.addHeaderCell(paragraph1)

        val paragraph2 = Paragraph().setMargin(2f)

        paragraph2.add("Quotation No.: ${quotation.id}\n")
        paragraph2.add("Date: ${quotation.date}\n")
        paragraph2.add("Packing Date: ${quotation.packingDate}\n")
        paragraph2.add("Moving Date: ${quotation.movingType}\n")

        table.addHeaderCell(paragraph2)

        // adding the other row in the table
        val paragraph3 = Paragraph().setMargin(2f).setFontSize(8f)

        paragraph3.add("Dear Sir/madam").setBold()
        paragraph3.add("\nWe thank you for valuable enquiry for packaging and shifting from to we are pleased to quote the rate for the same as under")

        val cell = Cell(1, 2).add(paragraph3)
        table.addCell(cell)

        // making single table and add in nested
        val leftParentTable = Table(1).useAllAvailableWidth()

        // making the moveFrom and move to table
        val moveToMoveFrom =
            Table(UnitValue.createPercentArray(2)).setMarginTop(0f).setMarginBottom(0f).useAllAvailableWidth()

        moveToMoveFrom.addHeaderCell(
            Paragraph("Move From").setBackgroundColor(DeviceRgb(0, 128, 0)).setFontColor(DeviceRgb(255, 255, 255))
                .setMargin(0f).setPaddingLeft(2f)
        )
        moveToMoveFrom.addHeaderCell(
            Paragraph("Move To").setBackgroundColor(DeviceRgb(0, 128, 0)).setFontColor(DeviceRgb(255, 255, 255))
                .setMargin(0f).setPaddingLeft(2f)
        )

        // move from paragraph
        val moveFromParagraph = Paragraph().setTextAlignment(TextAlignment.LEFT).setFontSize(10f)

        moveFromParagraph.add("City: ${quotation.city}\n")
        moveFromParagraph.add("State: ${quotation.state}\n")
        moveFromParagraph.add("Country: ${quotation.state2}\n")
        moveFromParagraph.add("Address: ${quotation.address}\n")

        moveToMoveFrom.addCell(Cell().add(moveFromParagraph))

        // move to paragraph
        val moveToParagraph = Paragraph().setTextAlignment(TextAlignment.LEFT).setFontSize(10f)
        moveToParagraph.add("City: ${quotation.city2}\n")
        moveToParagraph.add("State: ${quotation.state2}\n")
        moveToParagraph.add("Country: ${quotation.country2}\n")
        moveToParagraph.add("Address: ${quotation.address2}\n")


        moveToMoveFrom.addCell(moveToParagraph)

// additional optional
        val askingpara1 =
            Paragraph("Is there easy access for loading & unloading at location Move From & Move To : ${quotation.easyAccessForLoading}").setFontSize(
                8f
            )
        val askingparacell1 = Cell(1, 2).add(askingpara1)
        moveToMoveFrom.addCell(askingparacell1)

        val askingpara2 =
            Paragraph("Should any items be got down through balcony etc : ${quotation.shouldAnyItemGotDown}").setFontSize(
                8f
            )
        val askingparacell2 = Cell(1, 2).add(askingpara2)
        moveToMoveFrom.addCell(askingparacell2)

        val askingpara3 =
            Paragraph("Are there any restriction for loading/unloading at location Move From/Move To: ${quotation.areAnyRestrictionLoading}").setFontSize(
                8f
            )
        val askingparacell3 = Cell(1, 2).add(askingpara3)
        moveToMoveFrom.addCell(askingparacell3)

        val askingpara4 =
            Paragraph("Does You have any special needs or concerns : ${quotation.doesAnySpecialNeedsOrConcerns}").setFontSize(
                8f
            )
        val askingparacell4 = Cell(1, 2).add(askingpara4)
        moveToMoveFrom.addCell(askingparacell4)

        val askingpara5 =
            Paragraph("Insurance charge @3% on declaration value of goods").setBold().setFontSize(8f)
        val askingparacell5 = Cell(1, 2).add(askingpara5)
        moveToMoveFrom.addCell(askingparacell5)


        // sign and term condition
        val image = drawStamp(user.companyName)
        // Convert BufferedImage to byte array
        val baos = ByteArrayOutputStream()
        ImageIO.write(image, "png", baos)
        val imageData = ImageDataFactory.create(baos.toByteArray())
        val pdfimage = Image(imageData)
        pdfimage.setHeight(150f)
        pdfimage.setWidth(150f)

        val stampText = "Stamp"
        val fontSize = 10f
        val color = DeviceRgb(0, 128, 0)

        val stampParagraph = Paragraph(stampText)
            .setFontColor(color)
            .setFontSize(fontSize)
            .setTextAlignment(TextAlignment.CENTER)

        val stampCell = Cell().add(stampParagraph)
        moveToMoveFrom.addCell(pdfimage)

        // agree cell
        moveToMoveFrom.addCell(
            Paragraph("I Agree with Terms & Condition as Overleaf Signature Receiver's").setTextAlignment(
                TextAlignment.LEFT
            ).setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(6f)
        )


        val bankdetailspara = Paragraph().setFontSize(8f)
        bankdetailspara.add(Paragraph("\nBank Details:\n").setFontColor(DeviceRgb(0, 128, 0)))
        bankdetailspara.add("Beneficiary Name :\n")
        bankdetailspara.add("Bank Name :\n")
        bankdetailspara.add("Bank Ac No.\n")
        bankdetailspara.add("Bank IFSC Code :\n")

        moveToMoveFrom.addCell(bankdetailspara)

        //other option

        val otherbankoption = Paragraph().setHorizontalAlignment(HorizontalAlignment.CENTER)
            .setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(8f)
        otherbankoption.add(Paragraph("Other Payment Options:").setFontColor(DeviceRgb(0, 128, 0)))
        otherbankoption.add("\n \nPhonePe/Google Pay")

        moveToMoveFrom.addCell(otherbankoption)



        leftParentTable.addCell(Cell().add(moveToMoveFrom).setBorder(Border.NO_BORDER).setMargin(0f).setPadding(0f))
        table.addCell(leftParentTable)
        // adding the payment options

        val paymentTable = Table(2)
            .useAllAvailableWidth()
            .setBorder(Border.NO_BORDER)
            .setFontSize(10f)

        paymentTable.addHeaderCell(
            Cell().add(
                Paragraph("Particulars").setPaddingRight(5f).setPaddingLeft(5f).setBackgroundColor(DeviceRgb(0, 128, 0))
                    .setFontColor(DeviceRgb(255, 255, 255))
            )
        )
        paymentTable.addHeaderCell(
            Cell().add(
                Paragraph("Amount(Rs.)").setPaddingRight(5f).setPaddingLeft(5f).setBackgroundColor(DeviceRgb(0, 128, 0))
                    .setFontColor(DeviceRgb(255, 255, 255))
            )
        )

        paymentTable.addCell("Freight")
        paymentTable.addCell(quotation.freightCharge)
        paymentTable.addCell("Packing charge")
        paymentTable.addCell(quotation.packagingCharge)
        paymentTable.addCell("Un Packing Charge")
        paymentTable.addCell(quotation.unpackingCharge)
        paymentTable.addCell("Loading Charge")
        paymentTable.addCell(quotation.loadingCharge)
        paymentTable.addCell("Un Loading Charge")
        paymentTable.addCell(quotation.unloadingCharge)
        paymentTable.addCell("Pack. Material Charge")
        paymentTable.addCell(quotation.packingMaterialCharge)
        paymentTable.addCell("Other Charge")
        paymentTable.addCell(quotation.otherCharge)
        if (quotation.storageCharge!="0") {
            paymentTable.addCell("Storage Charge")
            paymentTable.addCell(quotation.storageCharge)
        }
        if (quotation.carBikeTpt!="0"){
            paymentTable.addCell("Car/Bike TPT")
            paymentTable.addCell(  quotation.carBikeTpt)
        }
        if (quotation.octroiGreenTAx!="0"){
            paymentTable.addCell("Octroi Green TAx")
            paymentTable.addCell(  quotation.octroiGreenTAx)
        }

        paymentTable.addCell(
            Cell().add(
                Paragraph("Sub Total").setPaddingRight(5f).setPaddingLeft(5f).setBackgroundColor(DeviceRgb(0, 128, 0))
                    .setFontColor(DeviceRgb(255, 255, 255))
            )
        )
        paymentTable.addCell(
            Cell().add(
                Paragraph("1000").setPaddingRight(5f).setPaddingLeft(5f).setBackgroundColor(DeviceRgb(0, 128, 0))
                    .setFontColor(DeviceRgb(255, 255, 255))
            )
        )

        paymentTable.addCell("SGST (9%)")
        paymentTable.addCell("${quotationOutput.gstin / 2}")
        paymentTable.addCell("CGST (9%)")
        paymentTable.addCell("${quotationOutput.gstin / 2}")
        paymentTable.addCell("Insurance Charge")
        paymentTable.addCell("${quotationOutput.insurance}")
        paymentTable.addCell("SubCharges")
        paymentTable.addCell("${quotationOutput.subcharges}")

        paymentTable.addCell(
            Cell().add(
                Paragraph("Total Amount").setPaddingRight(5f).setPaddingLeft(5f)
                    .setBackgroundColor(DeviceRgb(0, 128, 0))
                    .setFontColor(DeviceRgb(255, 255, 255))
            )
        )
        paymentTable.addCell(
            Cell().add(
                Paragraph("${quotationOutput.total}").setPaddingRight(5f).setPaddingLeft(5f)
                    .setBackgroundColor(DeviceRgb(0, 128, 0))
                    .setFontColor(DeviceRgb(255, 255, 255))
            )
        )


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
        querypara.add(Paragraph(user.id).setBold())


        document.add(querypara)
        document.close()
        return byteArrayOutputStream.toByteArray()
    }
}
fun drawStamp(name: String): BufferedImage {
    // Create BufferedImage with dimensions
    val width = 400
    val height = 400
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g2d = image.createGraphics()


    // Set rendering hints for better quality
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)


    // Set background to white
    g2d.color = Color.WHITE
    g2d.fillRect(0, 0, width, height)


    // Draw outer circle (thin)
    g2d.color = Color(68, 68, 132)
    g2d.stroke = BasicStroke(5f) // Set stroke width to 5 pixels
    g2d.drawOval(70, 70, 250, 250)


    // Draw inner circle
    g2d.drawOval(80, 80, 230, 230)


    // Rotate the graphics context
    val orig: AffineTransform = g2d.transform
    val at: AffineTransform = AffineTransform()
    at.rotate(Math.toRadians(-30.0), width / 2.0, height / 2.0)
    g2d.transform = at


    // Draw rounded rectangle with black border
    val rectX = 70
    val rectY = 160
    val rectWidth = 260
    val rectHeight = 60
    val arcWidth = 20
    val arcHeight = 20
    g2d.color = Color.WHITE
    g2d.stroke = BasicStroke(5f) // Set stroke width for rectangle border
    val roundedRectangle: RoundRectangle2D = RoundRectangle2D.Float(
        rectX.toFloat(),
        rectY.toFloat(),
        rectWidth.toFloat(),
        rectHeight.toFloat(),
        arcWidth.toFloat(),
        arcHeight.toFloat()
    )
    g2d.draw(roundedRectangle)
    g2d.fill(roundedRectangle)
    g2d.color = Color(68, 68, 132)

    // Write name inside the rectangle
    g2d.font = Font("Arial", Font.BOLD, 34)
    g2d.color = Color.BLUE
    val fm = g2d.fontMetrics
    val textX = rectX + (rectWidth - fm.stringWidth(name)) / 2
    val textY = rectY + ((rectHeight - fm.height) / 2) + fm.ascent
    g2d.drawString(name, textX, textY)


    // Reset the rotation
    g2d.transform = orig

    g2d.dispose()


    // Convert BufferedImage to byte array
    val baos = com.itextpdf.io.source.ByteArrayOutputStream()
    ImageIO.write(image, "png", baos)

    return image
}


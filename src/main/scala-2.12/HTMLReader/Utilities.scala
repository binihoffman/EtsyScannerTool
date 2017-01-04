package HTMLReader

import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.pdfbox
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.tools.TextToPDF

/**
  * Created by benh on 08/12/2016.
  */
object Utilities {

  def buildInitialSearchUrl(input: String): StringBuilder =
  {
    val output = new StringBuilder()
    val plus = "+"
    val array = input.split("\\s+")
    for (word <- array)
    {
      //println(word)
      output.append(word)
      output.append(plus)

    }
    return output.dropRight(1)
  }

  def createFileName(storeName: String, searchKey: String, numOfPages: String): String =
  {
    val dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
    val date = new Date()
    storeName + "_" + searchKey + "_" + numOfPages + "_" + dateFormat.format(date)
  }

  def createPDFReport(input: String): Unit =
  {
    val pdfCreator = new TextToPDF()
    val reader = new StringReader(input)
    val pdfReport = pdfCreator.createPDFFromText(reader)
    reader.close()
    pdfReport.close()
  }
}

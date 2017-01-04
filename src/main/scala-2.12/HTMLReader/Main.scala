package HTMLReader

import java.io.{FileOutputStream, PrintStream}
import java.util
object Main {

  //URL example: https://www.etsy.com/il-en/search?q=silver+earrings&explicit=1&page=1
  def main(args: Array[String]): Unit = {

    val storeName = args(0)
    val searchKey = args(1)
    val numOfPages = args(2)
    var numOfThreads = 10
    if (args.length > 3)
    {
      if (numOfThreads > 25) {
        numOfThreads = 25
      }
      else
      {
        numOfThreads = args(3).toInt
      }
    }

    runScanner(searchKey, searchKey, numOfPages, numOfThreads)


  }

  def runScanner (storeName: String, searchKey: String, numOfPages: String, numOfThreads: Int = 10): String =
  {
    val now = System.currentTimeMillis()
    //route the output to a file
    val filePathAndName = Utilities.createFileName(storeName,searchKey,numOfPages)
    val out = new PrintStream(new FileOutputStream("reports//" + filePathAndName))
    System.setOut(out)

    println("--------------------------------------------------------------------------------------------------------")
    println("------------------------------------Starting Scanning---------------------------------------------------")
    println("--------------------------------------------------------------------------------------------------------")
    println()
    println("Scanning search word - " + searchKey + ", in shop - " + storeName + ", for number of pages - " +numOfPages + ", with number of threads - " + numOfThreads)
    println()

    val threadList = new util.ArrayList[Thread]()
    for (i <- 1 to numOfThreads) {
      val thread = new Thread {
        override def run {
          val reader = new HTMLReader()
          reader.findRank(storeName, searchKey, numOfPages.toInt, i, numOfThreads)
        }
      }
      thread.start
      threadList.add(thread)
    }

    for(j <- 0 to threadList.size() - 1)
      threadList.get(j).join()

    //print the report

    println("--------------------------------------------------------------------------------------------------------")
    println("---------------------------------------Printing report--------------------------------------------------")
    println("--------------------------------------------------------------------------------------------------------")

    if (ReaderResponse.map.size < 1)
      {
        println("No items related to store - " + storeName + ", in first - " + numOfPages + " pages")
        println("Fix search for key - " + searchKey)
        println("Scanning took - " + (System.currentTimeMillis - now)) + "milliseconds"
        out.flush()
        out.close()
        return filePathAndName
      }
    ReaderResponse.map foreach (
      (t2) =>
      {
        println("--------Page " + t2._1  +"----")
        t2._2.printItem()
      }
      )
    println("Scanning took - " + (System.currentTimeMillis - now)) + "milliseconds"
    out.flush()
    out.close()
  //  Utilities.createPDFReport("reports//" +filePathAndName)
    return filePathAndName

  }
}

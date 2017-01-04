package HTMLReader

/**
  * Created by benh on 08/12/2016.
  */

import java.net.URL

import org.htmlcleaner.{HtmlCleaner, TagNode}


class HTMLReader() {

  val reader: HtmlCleaner = new HtmlCleaner()

  def readPage(address: String) : TagNode =
  {
    reader.clean(new URL(address))
  }
  def getInnerHTML(tagNode: TagNode) : String = {reader.getInnerHtml(tagNode)}

  def findShop(innerHtml: String, storeName: String) : Boolean =
  {
    innerHtml.contains(storeName)
  }

  def findRank(storeName: String, searchKey: String, maxPages: Int, mod: Int, numOfThreads: Int): Unit=
  {
    val formatedSearchKey = Utilities.buildInitialSearchUrl(searchKey)
    var url = "https://www.etsy.com/il-en/search?q="
    url = url + formatedSearchKey + "&explicit=1&page="
    var i = mod
    //go over all the HTML pages
    while (i < maxPages + 1)
    {
     // println("Searching page number - " + i)
      //println("Search URL is - " + url + i)
      val page = this.readPage(url + i)
      val innerHTML = this.getInnerHTML(page)
      //Look for the shop name in the HTML page
      if (this.findShop(innerHTML, storeName)) {
       // println("The store - " + storeName + ", with key - " + searchKey + " was found in page - "  + i)
        val elements = page.getElementsByName("a", true)
        for (elem <- elements){

          val classType = elem.getAttributeByName("class")
          if (classType != null && classType == "buyer-card card") {
            if (elem.getText.toString.contains(storeName)) {

              if (elem.findElementByAttValue("class", "card-meta-row-item text-truncate overflow-hidden card-shop-name", true, true) != null)
              {
                val item = new SearchItem(elem.getAttributeByName("href"), elem.getAttributeByName("title"))
                //println("Item found: ")
                //println("Link - " + elem.getAttributeByName("href"))
               // println("Title - " + elem.getAttributeByName("title"))
               // println("print item - " + i + item.printItem())
                ReaderResponse.addItemToResponse(i, item)

              }
         //     val ref = elem.getAttributeByName("href")


            }

          }
        }
      }
   //   val innerHTML = this.getInnerHTML(page)

      //if (this.findShop(innerHTML, storeName))
     // {
     //   println("The store - " + storeName + ", with key - " + searchKey + " was found in page - "  + i)
    //    return
    //  }
   //   println("The store - " + storeName + ", with key - " + searchKey + " was not found, fix items")
      i += numOfThreads

    }

  }




}

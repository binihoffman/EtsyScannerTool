package HTMLReader

/**
  * Created by benh on 08/12/2016.
  */
class SearchItem (val link: String, val title: String) {

  def printItem () =
  {
    println("Title of the item is - " + title)
    println("Link to the item is - " + link)
  }
}

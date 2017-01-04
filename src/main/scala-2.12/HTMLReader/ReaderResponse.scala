package HTMLReader



/**
  * Created by benh on 13/12/2016.
  */
object ReaderResponse {

  //val map: concurrent.Map[Int, SearchItem] = new ConcurrentHashMap[Int, SearchItem]().asScala
  val map = collection.mutable.SortedMap[Int, SearchItem]()

  def addItemToResponse(page: Int , searchItem: SearchItem) =
  {
    map.put(page, searchItem)
  }

  def getItem(key: Int): Option[SearchItem] =
  {
    map.get(key)
  }


}

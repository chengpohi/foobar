package nlp

object NLP {

  def main(args: Array[String]): Unit = {
    val seeds = List(
      "https://msdn.microsoft.com/en-us/library/ee658098.aspx",
      "https://msdn.microsoft.com/en-us/library/ee658124.aspx",
      "https://msdn.microsoft.com/en-us/library/ee658117.aspx",
      "https://msdn.microsoft.com/en-us/library/ee658084.aspx"
    )
    val crawler: Crawler = new Crawler
    val docs = crawler crawl seeds
    println(docs.docs.head tf "design")
  }

}

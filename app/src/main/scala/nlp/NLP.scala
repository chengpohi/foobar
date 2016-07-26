package nlp

object NLP {

  def main(args: Array[String]): Unit = {
    val seeds = List(
      Seed("https://msdn.microsoft.com/en-us/library/ee658098.aspx"),
      Seed("https://msdn.microsoft.com/en-us/library/ee658124.aspx"),
      Seed("https://msdn.microsoft.com/en-us/library/ee658117.aspx"),
      Seed("https://msdn.microsoft.com/en-us/library/ee658084.aspx")
    )
    val crawler: Crawler = new Crawler
    val docs = crawler crawl seeds
    import AnalyzedDocAlgorithm.TF
    import DocSetAlgorithm.IDF
    println(docs.head tf "stakeholders")
    println(docs idf "stakeholders")
    docs.tfIdf("stakeholders").foreach(i => {
      println(i._2)
    })
  }

}

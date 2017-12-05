package nlp.lucene

import java.io.{BufferedReader, InputStreamReader}
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document._
import org.apache.lucene.index.IndexWriterConfig.OpenMode
import org.apache.lucene.index.{DirectoryReader, IndexReader, IndexWriter, IndexWriterConfig}
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.{IndexSearcher, Query, TopDocs}
import org.apache.lucene.store.FSDirectory

object LuceneDemo extends App {
  IndexFiles
  SearchFiles
}

object IndexFiles {
  val indexPath = "target/lucene-index"
  val field = "content"

  val docFile: String = "build.sbt"

  val dir: FSDirectory = FSDirectory.open(Paths.get(indexPath))

  val analyzer = new StandardAnalyzer()
  val iwc = new IndexWriterConfig(analyzer)
  iwc.setOpenMode(OpenMode.CREATE)


  val writer = new IndexWriter(dir, iwc)
  indexDocs(writer, Paths.get(docFile))

  writer.close()


  def indexDocs(writer: IndexWriter, path: Path): Unit = {
    indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis)
  }

  def indexDoc(writer: IndexWriter, path: Path, lastModified: Long): Unit = {
    val stream = Files.newInputStream(path)
    val document = new Document()
    val pathField = new StringField("path", path.toString, Field.Store.YES)
    document.add(pathField)
    document.add(new LongPoint("modified", lastModified))
    document.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))))
    writer.addDocument(document)
    //writer.updateDocument(new Term("path", path.toString), document)
  }
}

object SearchFiles {
  val indexPath = "target/lucene-index"
  val reader: IndexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)))
  val searcher = new IndexSearcher(reader)
  val analyzer = new StandardAnalyzer
  val field = "contents"

  val parser = new QueryParser(field, analyzer)
  val line = "project"
  val query: Query = parser.parse(line)
  val docs: TopDocs = searcher.search(query, 100)
  println("docs size: " + docs.totalHits)
  docs.scoreDocs.foreach(f => println(f.toString))

  reader.close()
}

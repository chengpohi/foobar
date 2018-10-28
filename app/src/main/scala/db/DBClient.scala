package db

import java.sql.{Connection, ResultSet, Statement}
import java.util.concurrent.TimeUnit

import com.mysql.cj.jdbc.MysqlDataSource

import scala.util.Random

object DBClient extends App {
  def buildConnectionClient: Connection = {
    val dataSource = new MysqlDataSource
    dataSource.setUser("root")
    dataSource.setPassword("")
    dataSource.setServerName("localhost")
    dataSource.setPort(4000)
    dataSource.setDatabaseName("test")
    dataSource.getConnection()
  }
  def fetchAndPrint(sql: String, connection: Connection) = {
    val stmt: Statement = connection.createStatement()
    val rs: ResultSet = stmt.executeQuery(sql)
    while (rs.next()) {
      println(rs.getString("b"))
    }
    rs.close()
    stmt.close()
  }

  def update(sql: String, connection: Connection) = {
    val stmt = connection.createStatement()
    stmt.executeUpdate(sql)
  }

  val c1 = buildConnectionClient
  val c2 = buildConnectionClient
  val c3 = buildConnectionClient
  println("Initial Value")
  fetchAndPrint("select b from t where a = 1", c3)
  println("-" * 10)
  (0 to 10).foreach(i => {
    c1.setAutoCommit(false)
    c1.createStatement().execute("BEGIN")
    update("update t set b=2 where a=1", c2)
    fetchAndPrint("select b from t where a = 1", c1)
    update("update t set b=0 where a=1", c1)
    c1.commit()
  })
  c1.close()
  c2.close()
  c3.close()


}

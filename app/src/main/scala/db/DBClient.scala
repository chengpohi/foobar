package db

import java.sql.{Connection, ResultSet, Statement}

import com.mysql.cj.jdbc.MysqlDataSource

object DBClient extends App {
  val dataSource = new MysqlDataSource
  dataSource.setUser("root")
  dataSource.setPassword("")
  dataSource.setServerName("localhost")
  dataSource.setDatabaseName("shell_b2b")
  val connection: Connection = dataSource.getConnection()
  executeSql("select id from ticket")
  connection.close()

  private def executeSql(sql: String) = {
    val stmt: Statement = connection.createStatement()
    val rs: ResultSet = stmt.executeQuery(sql)
    while (rs.next()) {
      println(rs.getString("id"))
    }

    rs.close()
    stmt.close()
  }

}

package di

import scalaz.Reader

/**
  * Created by chengpohi on 25/02/2017.
  */
object ReaderDI {
  val userRepository = new UserRepository {
    override def get(id: Int) = User("t@t.com", 1, "", "")

    override def find(username: String) = User("t@t.com", 1, "", "")
  }

  def main(args: Array[String]): Unit = {
    val res = run(UserInfo.userEmail(1))
    println(res)
  }

  def run[T](reader: Reader[UserRepository, T]): T = {
    reader(userRepository)
  }
}

trait Config {
  def userRepository: UserRepository
}

object UserInfo extends Users {

  def userEmail(id: Int) = {
    getUser(id) map (_.email)
  }

  def userInfo(username: String) =
    for {
      user <- findUser(username)
      boss <- getUser(user.supervisorId)
    } yield
      Map(
        "fullName" -> s"${user.firstName} ${user.lastName}",
        "email" -> s"${user.email}",
        "boss" -> s"${boss.firstName} ${boss.lastName}"
      )
}

trait Users {

  import scalaz.Reader

  def getUser(id: Int) =
    Reader((userRepository: UserRepository) => userRepository.get(id))

  def findUser(username: String) =
    Reader((userRepository: UserRepository) => userRepository.find(username))
}

case class User(email: String,
                supervisorId: Int,
                firstName: String,
                lastName: String)

trait UserRepository {
  def get(id: Int): User

  def find(username: String): User
}

package algorithm

import scala.io.Source

/**
  * scala99
  * Created by chengpohi on 8/6/16.
  */
object Animal {
  def main(args: Array[String]): Unit = {
    val res: String = Source.fromFile(this.getClass.getResource("historyData.txt").getFile).getLines().toList.mkString(System.lineSeparator())
    println(getSnapShot(res, "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155"))
  }

  def getSnapShot(historyData: String, id: String): String = {
    val as: List[Option[FixedTimeAnimals]] = parse(historyData)
    !as.exists(_.isEmpty) match {
      case true =>
        val s = as.flatten
        validate(s) match {
          case None => s.find(_.id == id) match {
            case Some(c) => c.animals.get.mkString("\\n")
            case None => "Not Found"
          }
          case Some(t) => "Conflict found at " + t
        }
      case false => "Invalid format."
    }
  }

  def validate(as: List[FixedTimeAnimals]): Option[String] = {
    val res = as.zip(as.tail).flatMap(f =>
      for {
        f1 <- f._1.animals.get
        f2 <- f._2.animals.get.find(p => p.animal == f1.animal)
      } yield (f._2.id, f1.lastX + f1.xOffest.get == f2.lastX && f1.lastY + f1.yOffest.get == f2.lastY)
    )
    res.find(_._2 == false) match {
      case Some(res) => Some(res._1)
      case None => None
    }
  }

  def parse(s: String): List[Option[FixedTimeAnimals]] = {
    val res: Array[String] = s.split("(?=\\n.*\\n\\d+\\/\\d+\\/\\d+ \\d+\\:\\d+\\:\\d+)")
    res.map(r => {
      val filter: Array[String] = r.split("\\n").filter(!_.isEmpty)
      filter.length > 2 match {
        case true => {
          val animals: List[Option[Animal]] = filter.splitAt(2)._2.map(t => parseAnimal(t)).toList
          !animals.exists(_.isEmpty) match {
            case true => Some(FixedTimeAnimals(filter(0), filter(1), Some(animals.flatten)))
            case false => None
          }
        }
        case _ => None
      }
    }).toList
  }

  def parseAnimal(s: String): Option[Animal] = {
    s.split("\\s+") match {
      case Array(animal, lastX, lastY) => Some(Animal(animal, lastX.toInt, lastY.toInt))
      case Array(animal, lastX, lastY, xOffest, yOffest) =>
        Some(Animal(animal, lastX.toInt, lastY.toInt, Some(xOffest.toInt), Some(yOffest.toInt)))
      case _ => None
    }
  }

  case class Animal(animal: String, lastX: Int, lastY: Int, xOffest: Option[Int] = Some(0), yOffest: Option[Int] = Some(0))
  case class FixedTimeAnimals(id: String, name: String, animals: Option[List[Animal]] = None)
}


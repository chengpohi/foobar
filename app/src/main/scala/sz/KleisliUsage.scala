package sz

object KleisliUsage extends App {

  import scalaz._
  import Scalaz._
  import Kleisli._

  import scala.util._

  case class Continent(name: String, countries: List[Country] = List.empty)

  case class Country(name: String, cities: List[City] = List.empty)

  case class City(name: String,
                  isCapital: Boolean = false,
                  inhabitants: Int = 20)

  val data: List[Continent] = List(
    Continent("Europe"),
    Continent("America",
              List(Country("USA", List(City("Washington"), City("New York"))))),
    Continent("Asia",
              List(Country("India", List(City("New Dehli"), City("Calcutta")))))
  )

  def continents(name: String): List[Continent] =
    data.filter(k => k.name.contains(name))

  def countries(continent: Continent): List[Country] = continent.countries

  def cities(country: Country): List[City] = country.cities

  def save(cities: List[City]): Try[Unit] =
    Try {
      cities.foreach(c => println("Saving " + c.name))
    }

  def inhabitants(c: City): Int = c.inhabitants

  //andThenK = this >=> kleisli(_)
  val allCities = kleisli(continents) >==> countries >==> cities

  //andThen
  val allCities2 = kleisli(continents) >=> kleisli(countries) >=> kleisli(
    cities)

  (allCities("America")).map(println)
  (allCities =<< List("Amer", "Asi")).map(println)

  //map kleisli
  val cityInhabitants = allCities map inhabitants
  (cityInhabitants) =<< List("Amer", "Asi")

  val getandSave = (allCities mapK save)
  getandSave("America").map(println)

  // local can be used to prepend a kleisli function of
  // the form A => M[B] with a function of the form
  // AA => A, resulting in a kleisli function of the form
  // AA => M[B]
  def index(i: Int) = data(i).name

  val allCitiesByIndex = allCities local index
  allCitiesByIndex(1).map(println)
}

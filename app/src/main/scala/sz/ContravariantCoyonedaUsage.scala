package sz

import scalaz.effect.SafeApp
import scalaz._
import Scalaz._
import scalaz.{\/, ContravariantCoyoneda => CtCoyo, Monoid, Order}

object ContravariantCoyonedaUsage extends SafeApp {
  // Suppose I have some unstructured data.
  val unstructuredData: List[Vector[String]] =
    List(Vector("Zürich", "807", "383,708"),
      Vector("東京", "1868", "13,185,502"),
      Vector("Brisbane", "1824-09-13", "2,189,878"),
      Vector("München", "1158", "1,388,308"),
      Vector("Boston", "1630-09-07", "636,479"))

  def numerically1: Order[String] =
    Order.order((a, b) => parseCommaNum(a) ?|? parseCommaNum(b))

  def numerically2: Order[String] =
    Order orderBy parseCommaNum

  def numerically3: Order[String] =
    Order[Long \/ String] contramap parseCommaNum

  def parseCommaNum(s: String): Long \/ String =
    ("""-?[0-9,]+""".r findFirstIn s
      flatMap (_.filter(_ != ',').parseLong.toOption)) <\/ s


  def caseInsensitively(s: String): String =
    s.toUpperCase.toLowerCase

  def parseDate(s: String): (Int, Option[(Int, Int)]) \/ String =
    (for {
      grps <- """([0-9]+)-([0-9]+)-([0-9]+)""".r findFirstMatchIn s
      List(y, m, d) <- grps.subgroups.traverse(_.parseInt.toOption)
    } yield (y, Some((m, d))))
      .orElse(for {
        n <- """[0-9]+""".r findFirstIn s
        yi <- n.parseInt.toOption
      } yield (yi, None)) <\/ s

  def caseInsensitivelyOrd: Order[String] =
    Order orderBy caseInsensitively

  def dateOrd: Order[String] =
    Order orderBy parseDate

  def schwartzian[A, B](xs: List[A])(f: A => B)(implicit B: Order[B]): List[A] =
    xs.map(a => (a, f(a)))
      .sortBy(_._2)(B.toScalaOrdering)
      .map(_._1)

  def nonschwartzian[A, B](xs: List[A])(f: A => B)(implicit B: Order[B]): List[A] =
    xs.sorted(Order.orderBy(f).toScalaOrdering)

  val byDirectSorts: List[List[Vector[String]]] =
    List(schwartzian(unstructuredData)(v => caseInsensitively(v(0))),
      schwartzian(unstructuredData)(v => parseDate(v(1))),
      schwartzian(unstructuredData)(v => parseCommaNum(v(2))))

  val byOrdListSorts: List[List[Vector[String]]] = for {
    (ord, i) <- List((caseInsensitivelyOrd, 0),
      (dateOrd, 1),
      (numerically2, 2))
  } yield unstructuredData.sortBy(v => v(i))(ord.toScalaOrdering)

  val numerically4: CtCoyo[Order, String] =
    CtCoyo(Order[Long \/ String])(parseCommaNum)

  val CCOrder = CtCoyo.by[Order]

  val decomposedSortKeys: List[(CtCoyo[Order, String], Int)] =
    List((CCOrder(caseInsensitively), 0),
      (CCOrder(parseDate), 1),
      (numerically4, 2))

  val bySchwartzianListSorts: List[List[Vector[String]]] = for {
    (ccord, i) <- decomposedSortKeys
  } yield schwartzian(unstructuredData)(v => ccord.k(v(i)))(ccord.fi)

  val bySchwartzianListSortsTP: List[List[Vector[String]]] = for {
    (ccord, i) <- decomposedSortKeys
  } yield (schwartzian[Vector[String], ccord.I]
    (unstructuredData)(v => ccord.k(v(i)))(ccord.fi))

  sealed abstract class SortType

  object SortType {

    case object CI extends SortType

    case object Dateish extends SortType

    case object Num extends SortType

  }

  type SortSpec = List[(SortType, Int)]


  val mainLtoRsort: SortSpec =
    List((SortType.CI, 0), (SortType.Dateish, 1), (SortType.Num, 2))

  def sortTypeOrd(s: SortType): CtCoyo[Order, String] = s match {
    case SortType.CI => CCOrder(caseInsensitively)
    case SortType.Dateish => CCOrder(parseDate)
    case SortType.Num => CCOrder(parseCommaNum) // like numerically4
  }

  def recItemOrd(i: Int, o: CtCoyo[Order, String]): CtCoyo[Order, Vector[String]] =
    o contramap (v => v(i))

  def unitOrd[A]: CtCoyo.Aux[Order, A, Unit] = CCOrder(a => ())

  def ordFanout[A](l: CtCoyo[Order, A], r: CtCoyo[Order, A]): CtCoyo.Aux[Order, A, (l.I, r.I)] = {
    implicit val lfi: Order[l.I] = l.fi
    implicit val rfi: Order[r.I] = r.fi
    CCOrder(l.k &&& r.k)
  }

  def sortSpecOrd(s: SortSpec): CtCoyo[Order, Vector[String]] =
    s.foldRight(unitOrd: CtCoyo[Order, Vector[String]]) { (sti, acc) =>
      val (st, i) = sti
      ordFanout(recItemOrd(i, sortTypeOrd(st)), acc)
    }

  def sortDataBy(xs: List[Vector[String]], o: SortSpec)
  : List[Vector[String]] = {
    val coyo = sortSpecOrd(o)
    schwartzian(xs)(coyo.k)(coyo.fi)
  }

  val sortedBySpec: List[Vector[String]] =
    sortDataBy(unstructuredData, mainLtoRsort)

  println("sortedBySpec: " |+| sortedBySpec.shows)

  val sortedByNonCity: List[Vector[String]] =
    sortDataBy(unstructuredData, mainLtoRsort.tail)

  println("sortedByNonCity: " |+| sortedByNonCity.shows)

  val mainLtoRcoyo: CtCoyo[Order, Vector[String]] =
    sortSpecOrd(mainLtoRsort)

  val mainLtoRtailcoyo: CtCoyo[Order, Vector[String]] =
    sortSpecOrd(mainLtoRsort.tail)

  println("list of mainLtoRcoyo.I: " |+|
    unstructuredData.map(r => mainLtoRcoyo.k(r).toString).shows)
  println("list of mainLtoRtailcoyo.I: " |+|
    unstructuredData.map(r => mainLtoRtailcoyo.k(r).toString).shows)

  def sortSpecOrdL(s: SortSpec): CtCoyo[Order, Vector[String]] =
    s.foldLeft(unitOrd: CtCoyo[Order, Vector[String]]){(acc, sti) =>
      val (st, i) = sti
      ordFanout(acc, recItemOrd(i, sortTypeOrd(st)))
    }

  val mainLtoRcoyoL: CtCoyo[Order, Vector[String]] =
    sortSpecOrdL(mainLtoRsort)

  println("list of mainLtoRcoyoL.I: " |+|
    unstructuredData.map(r => mainLtoRcoyoL.k(r).toString).shows)

  val sortedByNonCityL: List[Vector[String]] = {
    val coyo = sortSpecOrdL(mainLtoRsort.tail)
    schwartzian(unstructuredData)(coyo.k)(coyo.fi)
  }



  println("sortedByNonCityL: " |+| sortedByNonCity.shows)

  implicit def ctCoyoOrdMonoid[A]: Monoid[CtCoyo[Order, A]] =
    Monoid instance (ordFanout(_, _), unitOrd)

  def sortSpecOrdF(s: SortSpec): CtCoyo[Order, Vector[String]] =
    s.foldMap{case (st, i) => recItemOrd(i, sortTypeOrd(st))}

  trait Binfmt[A] {
    def describe: String
  }

  object Binfmt {
    def apply[A](s: String): Binfmt[A] = new Binfmt[A] {val describe = s}

    implicit val descLong: Binfmt[Long] = Binfmt("<Long>")
    implicit val descInt: Binfmt[Int] = Binfmt("<Int>")
    implicit val descString: Binfmt[String] = Binfmt("<String>")
    implicit val descUnit: Binfmt[Unit] = Binfmt("")
    implicit def descOption[A](implicit a: Binfmt[A])
    : Binfmt[Option[A]] = Binfmt("?<" |+| a.describe |+| ">")
    implicit def desc_\/[A, B](implicit a: Binfmt[A], b: Binfmt[B])
    : Binfmt[A \/ B] = Binfmt("<" |+| a.describe |+| "\\/"
      |+| b.describe |+| ">")
    implicit def desc2Tuple[A, B](implicit a: Binfmt[A], b: Binfmt[B])
    : Binfmt[(A, B)] = Binfmt(a.describe |+| b.describe)
  }

  type BinOrd[A] = (Binfmt[A], Order[A])

  def CCBinOrd[A, B](f: A => B)(implicit b: Binfmt[B], o: Order[B])
  : CtCoyo.Aux[BinOrd, A, B] =
    CtCoyo[BinOrd, A, B]((b, o))(f)

  def sortTypeBinOrd(s: SortType): CtCoyo[BinOrd, String] = s match {
    case SortType.CI => CCBinOrd(caseInsensitively)
    case SortType.Dateish => CCBinOrd(parseDate)
    case SortType.Num => CCBinOrd(parseCommaNum)
  }

  // It turns out that recItemOrd is completely abstract:

  def recItem[F[_]](i: Int, o: CtCoyo[F, String])
  : CtCoyo[F, Vector[String]] =
    o contramap (v => v(i))

  // And, again, with a general notion of the underlying product
  // function and identity you could probably produce the monoid
  // generically, but for now:

  def unitBinOrd[A]: CtCoyo.Aux[BinOrd, A, Unit] = CCBinOrd(a => ())

  def binOrdFanout[A](l: CtCoyo[BinOrd, A], r: CtCoyo[BinOrd, A])
  : CtCoyo.Aux[BinOrd, A, (l.I, r.I)] = {
    implicit val (lfb, lfo) = l.fi
    implicit val (rfb, rfo) = r.fi
    CCBinOrd(l.k &&& r.k)
  }

  // I’ve chosen the `Binfmt' instances with a bit of care to preserve
  // their monoid-hood.  That isn’t a requirement for you to do this
  // kind of abstracting, though.

  implicit def ctCoyoBinOrdMonoid[A]: Monoid[CtCoyo[BinOrd, A]] =
    Monoid instance (binOrdFanout(_, _), unitBinOrd)

  def sortSpecBinOrdF(s: SortSpec): CtCoyo[BinOrd, Vector[String]] =
    s.foldMap{case (st, i) => recItem[BinOrd](i, sortTypeBinOrd(st))}

  // The drawback here is that I can’t just build a separate stack
  // willynilly for `Binfmt'.  I have to prove at each step that *the
  // same* `I' is used for the `Binfmt' and the `Order' for this to be
  // useful.  Once again, an idea of a fold step that can be fused
  // with the `Order' construction safely can be abstracted out here.

  val (binfmtdesc, finalsort) = {
    val bo = sortSpecBinOrdF(mainLtoRsort)
    (bo.fi._1.describe,
      schwartzian(unstructuredData)(bo.k)(bo.fi._2))
  }

  // For your edification, the binfmtdesc is:
  //
  //   <String><<Int>?<<Int><Int>>\/<String>><<Long>\/<String>>
  //
  // But you should just sbt "example/runMain
  // scalaz.example.ContravariantCoyonedaUsage" to see this and the
  // other prints in action.

  println("binfmtdesc: " |+| binfmtdesc)
  println("finalsort: " |+| finalsort.shows)

  // [1] https://en.wikipedia.org/wiki/Schwartzian_transform
  // [2] http://failex.blogspot.com/2013/06/fake-theorems-for-free.html
  // [3] https://github.com/scodec/scodec
  // [4] https://github.com/joshcough/f0


}

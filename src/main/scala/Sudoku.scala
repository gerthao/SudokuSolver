import cats.syntax.all.*

final case class Sudoku private (data: Vector[Int])

object Sudoku:
  val PartLength  = 3
  val Length: Int = PartLength * PartLength
  val Size: Int   = Length * Length

  def from(s: String): Either[String, Sudoku] =
    s.replace('.', '0')
      .asRight
      .ensure("Sudoku board does not contain only digits")(_.forall(_.isDigit))
      .map(_.toVector.map(_.asDigit))
      .ensure(s"Sudoku board is not $Size elements in length")(_.length == Sudoku.Size)
      .map(Sudoku.apply)

  extension (s: Sudoku)
    def get(x: Int)(y: Int): Int       = s.data(y * Length + x)
    def getRow(y: Int): Vector[Int]    = s.data.slice(y * Length, (y + 1) * Length)
    def getColumn(x: Int): Vector[Int] = (0 until Length).toVector.map(get(x))

    def getCellOf(x: Int)(y: Int): Vector[Int] =
      for
        newX <- span(x)
        newY <- span(y)
      yield get(newX)(newY)

    def getZero: Option[(Int, Int)] =
      Option(s.data.indexWhere(_ === 0))
        .filterNot(_ === -1)
        .map(cc => (cc % Length, cc / Length))

    def fitsInPlace(x: Int, y: Int)(value: Int): Boolean =
      !(getCellOf(x)(y).contains(value) ||
        getRow(y).contains(value) ||
        getColumn(x).contains(value))

    def set(x: Int, y: Int)(value: Int): Sudoku =
      Sudoku(s.data.updated(y * Length + x, value))

    def prettyString: String =
      def printRow(a: Vector[Int]): String =
        a.grouped(PartLength).map(_.mkString).mkString("|")

      (0 until Length)
        .map(getRow)
        .map(printRow)
        .grouped(PartLength)
        .map(_.mkString("\n"))
        .mkString("\n---+---+---\n")

    def show(): Unit = prettyString |> println

    private def span(n: Int): Vector[Int] =
      val x: Int = PartLength * (n / PartLength)
      (0 until PartLength).toVector.map(_ + x)
end Sudoku

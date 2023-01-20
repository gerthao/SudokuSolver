import munit._
import org.scalacheck.Prop._

class SudokuSpec extends FunSuite:
  val maybeSudoku: Either[String, Sudoku] = Sudoku.from(
    "435269781" +
    "682571493" +
    "197834562" +
    "826195347" +
    "374682915" +
    "951743628" +
    "519326874" +
    "248957136" +
    "763418259"
  )

  val sudokuF: Either[String, Sudoku] => FunFixture[Sudoku] = e => FunFixture[Sudoku](
    _ => e.fold(failSuite(_), identity),
    _ => ()
  )

  sudokuF(maybeSudoku).test("Sudoku(x)(y) should get numbers at x and y")(sudoku =>
    assertEquals(sudoku.get(0)(0), 4)
    assertEquals(sudoku.get(1)(0), 3)
  )

  val sudokuString: Either[String, Sudoku]  = Sudoku.from("....47......5.....9.483..15.19.7...4...3.9.21.3...5.7......8....78.2..3...1.5.4..")
  val sudokuString2: Either[String, Sudoku] = Sudoku.from("." * 81)
  sudokuF(sudokuString).test("Sudoku with a valid input can be solved")(sudoku =>
    val result  = SudokuSolver().solve(sudoku).headOption
    result.foreach(_.show())
    assert(result.isDefined)
  )

  sudokuF(sudokuString2).test("Sudoku with another valid input can be solved")(sudoku =>
    val result = SudokuSolver().solve(sudoku).headOption
    result.foreach(_.show())
    assert(result.isDefined)
  )
end SudokuSpec


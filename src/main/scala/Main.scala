import com.monovore.decline
import cats.syntax.all.*
import com.monovore.decline.{CommandApp, Opts}

private val sudokuArgument: Opts[Sudoku] =
  Opts.argument[String]("sudoku")
    .mapValidated(Sudoku.from(_).toValidatedNel)

private def handleResult(result: Option[Sudoku]): Unit = result match
  case None =>
    System.err.println("Sudoku is not solvable")
    System.exit(1)
  case Some(solved) =>
    println(solved)
    System.exit(0)

object Main extends CommandApp(
  name   = "sudokuSolver",
  header = "Solves sudokus as 81 chars passed in with . as empty space",
  main   = sudokuArgument.map(sudoku => SudokuSolver().solve(sudoku).headOption |> handleResult)
)

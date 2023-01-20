import cats.syntax.all.*

class SudokuSolver:
  def solve(sudoku: Sudoku): Seq[Sudoku] = sudoku.getZero.fold(LazyList(sudoku))((x, y) => 
    sudoku |> calcStep(x, y) >>= solve
  )

  private def calcStep(x: Int, y: Int)(s: Sudoku): Seq[Sudoku] =
    LazyList(1 to Sudoku.Length: _*)
      .filter(s.fitsInPlace(x, y))
      .map(s.set(x, y))

end SudokuSolver


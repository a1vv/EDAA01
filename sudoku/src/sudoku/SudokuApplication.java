package sudoku;

public class SudokuApplication {

	public static void main(String[] Args) {
		SudokuSolverClass solver = new SudokuSolverClass();
		SudokuController controller = new SudokuController(solver);

	}
}

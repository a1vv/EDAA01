package sudoku;

import java.awt.*;
import javax.swing.*;

public class SudokuController {
	private SudokuSolverClass solver;
	private JTextField[][] cells;
	private JPanel sudokuPanel;

	public SudokuController(SudokuSolverClass solver) {
		this.solver = solver;
		int size = solver.getSize();
		this.cells = new JTextField[size][size];
		SwingUtilities.invokeLater(() -> createWindow("Sudoku Solver", size * 60, size * 60));
	}

	private void createWindow(String title, int height, int width) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Couldn't change look and feel.");
		}

		int size = solver.getSize();

		// create new frame
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// get the contentPane from frame
		Container pane = frame.getContentPane();

		// create new gridlayout
		GridLayout grid = new GridLayout(size, size, 0, 0);

		// create panel for gridlayout
		this.sudokuPanel = new JPanel(grid);

		updateGrid(true);

		// create bottompanel with buttons
		JButton btnSolve = new JButton("Solve");
		btnSolve.addActionListener(p -> {
			solve();
		});
		btnSolve.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSolve.setMargin(new Insets(10, 10, 10, 10));

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(p -> {
			clear();
		});
		btnClear.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnClear.setMargin(new Insets(10, 10, 10, 10));

		// add button to switch between premade sudokus
		JButton btnSwitch = new JButton("Next");
		btnSwitch.addActionListener(p -> {
			cycleThroughPresets();
		});
		btnSwitch.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSwitch.setMargin(new Insets(10, 10, 10, 10));

		JPanel bottomPanel = new JPanel(new BorderLayout(0, 0));
		bottomPanel.add(btnClear, BorderLayout.WEST);
		bottomPanel.add(btnSolve, BorderLayout.CENTER);
		bottomPanel.add(btnSwitch, BorderLayout.EAST);
		pane.add(bottomPanel, BorderLayout.SOUTH);
		frame.getRootPane().setDefaultButton(btnSolve);

		pane.add(sudokuPanel);
		frame.pack();
		frame.setSize(new Dimension(width, height));
		frame.setVisible(true);
	}

	private void cycleThroughPresets() {
		solver.clear();
		solver.cycleThroughPresets();
		updateGrid(false);
	}

	private void clear() {
		solver.clear();
		updateGrid(false);
	}

	private void solve() {

		int size = solver.getSize();

		// convert jtextfield array into an int array
		int[][] matrix = new int[size][size];
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				String text = cells[r][c].getText();
				if (text.equals("0")) {
					System.out.println("Snopp");
					matrix[r][c] = 20000;
				} else if (text.equals("")) {
					matrix[r][c] = 0;
				} else {
					try {
						matrix[r][c] = Integer.parseInt(text);
					} catch (Exception e) {
						matrix[r][c] = -1;
					}
				}
			}
		}

		// try to solve
		long startTime = System.currentTimeMillis();
		boolean solvable = false;
		boolean valid = false;
		try {
			// update the matrix
			solver.setMatrix(matrix);
			valid = solver.isValid();
			solvable = valid ? solver.solve() : false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(sudokuPanel, e.getMessage());
		}

		long time = (System.currentTimeMillis() - startTime);
		System.out.println(solvable);

		if (solvable) {
			updateGrid(false);
			JOptionPane.showMessageDialog(sudokuPanel, "SOLVED IN " + time + " ms.");
		} else if(valid) {
			JOptionPane.showMessageDialog(sudokuPanel, "UNSOLVABLE (" + time + " ms)");
		}
		System.out.println("Recursion count: " + solver.getCount());
	}

	/**
	 * Update the JTextFields on screen
	 * 
	 * @param startup
	 */
	private void updateGrid(boolean startup) {
		int[][] matrix = solver.getMatrix();
		int size = solver.getSize();
		if (startup) {
			// populate gridlayout with textfields
			for (int r = 0; r < size; r++) {
				for (int c = 0; c < size; c++) {
					cells[r][c] = new JTextField();
					cells[r][c].setHorizontalAlignment(JTextField.CENTER);
					cells[r][c].setFont(new Font("SansSerif", Font.BOLD, 20));
					cells[r][c].setBackground(generateColor(r, c, true));
					cells[r][c].setBorder(BorderFactory.createLineBorder(Color.white, 1));
					sudokuPanel.add(cells[r][c]);
				}
			}
		}
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				String value = "" + matrix[r][c];
				if (value.equals("0")) {
					value = "";
				}
				cells[r][c].setText("" + value);
			}
		}
	}

	/**
	 * Given a row and column, return a colur
	 * 
	 * @param row
	 * @param col
	 * @return background color
	 */
	private Color generateColor(int row, int col, boolean background) {

		int sizeRoot = (int) Math.sqrt(solver.getSize());
		int squareRow = row / sizeRoot;
		int squareCol = col / sizeRoot;

		if ((squareRow % 2 == 0) && (squareCol % 2 == 0) || (squareRow % 2 == 1 && squareCol % 2 == 1)) {
			return new Color(255, 168, 143);
		}
		return new Color(255, 204, 143);
	}
}

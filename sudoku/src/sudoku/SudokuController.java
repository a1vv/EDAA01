package sudoku;

import java.awt.*;
import javax.swing.*;

public class SudokuController {
	private SudokuSolverClass solver;
	private JTextField[][] cells;
	private JFrame frame;
	
	public SudokuController(SudokuSolverClass solver) {
		this.solver = solver;
		int snopp = solver.getSize();
		this.cells = new JTextField[snopp][snopp];
		SwingUtilities.invokeLater(() -> createWindow( "Sudoku Solver", 500, 550));
	}

	private void  createWindow(String title, int height, int width) {
		
		int snopp = solver.getSize();
		
		// create new frame
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// get the contentPane from frame
		Container pane = frame.getContentPane();
		
		// create new gridlayout
		GridLayout grid = new GridLayout(snopp, snopp,0,0);
		
		// create panel for gridlayout
		JPanel panel = new JPanel(grid);
		
		// populate gridlayout with textfields
		for (int r = 0; r < 9; r++) {
           for (int c = 0; c < 9; c++) { 
        	   cells[r][c] = new JTextField();
        	   cells[r][c].setHorizontalAlignment(JTextField.CENTER);        	 
        	   cells[r][c].setFont(new Font("SansSerif", Font.BOLD, 20));
        	   cells[r][c].setBackground(squareBackground(r,c));
               panel.add(cells[r][c]);
           	}
		}
		
		// create bottompanel with buttons
		JButton btnSolve = new JButton("Solve");
		btnSolve.addActionListener(p->{
			solve();
		});
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(p->{
			solver.clear();
			updateGrid();
		});
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(btnSolve, BorderLayout.WEST);
		bottomPanel.add(btnClear, BorderLayout.EAST);
		pane.add(bottomPanel, BorderLayout.SOUTH);
		frame.getRootPane().setDefaultButton(btnSolve);
	
		pane.add(panel);
		frame.pack();
		frame.setSize(new Dimension(width, height));
		frame.setVisible(true);
	}
	
	private void solve() {
		
		// convert jtextfield array into an int array
		int[][] matrix = new int[9][9];
		for (int r = 0; r < 9; r++) {
           for (int c = 0; c < 9; c++) { 	
    		   String text = cells[r][c].getText();
    		   if(text.equals("")) {
    			   matrix[r][c] = 0;
    		   } else {
    			   try {
    	        	   matrix[r][c] = Integer.parseInt(text);
            	   } catch (Exception e) {
            		   JOptionPane.showMessageDialog(frame,"Entry has to be an integer");
            	   }
    		   }
        	   
        	  
           }
		}
		
		// update the matrix
		solver.setMatrix(matrix);
		// try to solve it
		boolean solvable = solver.solve();
		System.out.println(solvable);
		if( solvable ) {
			updateGrid();
		} else {

 		   JOptionPane.showMessageDialog(frame,"UNSOLVABLE");
		}
		
	}
	
	
	// TODO: expandera denna funktion så att den täcker det fall när inga textfields finns, som är skrivet i createWindow atm.
	private void updateGrid() {
		int[][] matrix = solver.getMatrix();
		for (int r = 0; r < 9; r++) {
           for (int c = 0; c < 9; c++) { 	   
        	  // System.out.println("setting value in " + r + c + " to " + matrix[r][c] );
        	   String value = ""+matrix[r][c];
        	   if(value.equals("0")) {
        		   value = "";
        	   }
               cells[r][c].setText(""+value);
           	}
		}
	}
	
	private Color squareBackground(int row, int col) {
		
		int sizeRoot = (int) Math.sqrt(solver.getSize()); 
		int squareRow = row/sizeRoot;
		int squareCol = col/sizeRoot;
		
		if( (squareRow % 2 == 0 ) && (squareCol % 2 == 0) || (squareRow == 1 && squareCol == 1)) {
			return Color.lightGray;
		}
		/*
		if((squareRow == 0 || squareRow == 2 ) && (squareCol == 0 || squareCol == 2) || (squareRow == 1 && squareCol == 1)) {
			return Color.lightGray;
		}
		*/
		return Color.white;
		
		
	}
}

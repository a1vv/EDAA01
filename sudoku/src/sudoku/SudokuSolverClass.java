package sudoku;

import java.util.ArrayList;

public class SudokuSolverClass implements SudokuSolver {
	
	private int recursionCount = 0;
	private int currentTestMatrix = 0;
	private int[][] matrix;
	private final int size = 9;

	
	public SudokuSolverClass(){
		this.matrix = new int[size][size];
	}

	/**
	 * When called: Cycle through different preset sudokus and set matrix
	 */
	public void cycleThroughPresets() {
		int[][] tm1 = { {3, 0, 6, 5, 0, 8, 4, 0, 0}, {5, 2, 0, 0, 0, 0, 0, 0, 0}, 
				{0, 8, 7, 0, 0, 0, 0, 3, 1}, {0, 0, 3, 0, 1, 0, 0, 8, 0}, {9, 0, 0, 8, 6, 3, 0, 0, 5}, 
				{0, 5, 0, 0, 9, 0, 6, 0, 0}, {1, 3, 0, 0, 0, 0, 2, 5, 0}, {0, 0, 0, 0, 0, 0, 0, 7, 4}, 
				{0, 0, 5, 2, 0, 6, 3, 0, 0} };
		
		int[][] tm2 = {{0, 0, 2, 1, 0, 4, 3, 0, 0},  {0, 4, 0, 8, 0, 7, 0, 5, 0}, 
				{8, 0, 1, 0, 9, 0, 7, 2, 0}, {2, 0, 5, 0, 0, 3, 0, 0, 0}, {0, 8, 0, 0, 0, 1, 0, 0, 3},
		        {0, 1, 0, 4, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0, 5, 0, 2}, {0, 0, 4, 0, 1, 5, 6, 0, 0},
		        {0, 0, 8, 0, 3, 0, 0, 0, 7}};
		
		 int[][] tm3 = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
				{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
		 
		 int[][] tm4 = {{0, 0, 0, 0, 0, 0, 3, 8, 6},  {6, 4, 0, 0, 0, 0, 0, 9}, 
					{8, 0, 0, 0, 0, 0, 0, 2, 4}, {2, 0, 5, 9, 0, 0, 0, 4, 1}, {4, 8, 9, 5, 0, 0, 0, 7, 3},
			        { 0, 0, 0, 4, 0, 0, 9, 6, 5}, {1, 3, 6, 0, 0, 0, 0, 0, 0}, {9, 0, 0, 0, 1, 5, 0, 0, 0},
			        {5, 0, 0, 0, 3, 9, 4, 1, 7}};
		 
		 ArrayList<int[][]> testSudokus = new ArrayList<int[][]>();
		 
		 testSudokus.add(tm1);
		 testSudokus.add(tm2);
		 testSudokus.add(tm3);
		 
		 // go to next testSudoku
		 if(currentTestMatrix == testSudokus.size()-1) {
			 currentTestMatrix = 0;
		 } else {
			 currentTestMatrix++;
		 }
		 
		 // set the matrix to testSuduoku
		 this.matrix = testSudokus.get(currentTestMatrix);
	}
	
	
	/**
	 * Attempts to solve the sudoku
	 * @return true if solveable
	 */
	@Override
	public boolean solve() {
		resetCount();
		// if checkInput is false return false without wasting time trying to solve
		return solve( 0, 0 );
		 
	}
	
	/**
	 * check all the inputted numbers to see if they are allowed (instead of trying to solve a clearly unsolvable sudoku)
	 * @return true if inputs are allowed
	 */
	private boolean checkInputs() {
		for(int r = 0 ; r < size ; r++) {
			for(int c = 0 ; c < size ; c++) {
				
				if(isInputAllowed(matrix[r][c]) ) {
					// if the input in the cell is not valid, return false. Otherwise keep searching
					if( !isNumberValid(r,c) ) {
						return false;
					}
				}
			}
		}
		// there was no input
		return true;
	}
	
	/**
	 *  return true if integer num is in range
	 * @param num the input to check
	 * @return true if the input is in range
	 */
	private boolean isInputAllowed(int num) {
		if( num > 0 && num <= size) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Recursive solve method
	 * @param row: current row to check
	 * @param col: current column to check
	 * @return true if entire sudoku could be solved
	 */
	private boolean solve(int row, int col ) {
		recursionCount++;
		// if column is larger than size, go to first cell on next row next row 
		if(col >= size) {
			col = 0;
			row += 1;
		}
		
		// if entire matrix has been traversed without failure, solve returns true, matrix should now be changed.
		if(row >= size) {
			return true;
		}
		
		// if cell is empty (0), try numbers 
		if( matrix[row][col] == 0 ) {
			
			// loop through numbers until one works
			for(int i = 1 ; i <= size ; i++) {
				
				// set number in cell 
				matrix[row][col] = i;
				
				// check if number is valid
				if( isNumberValid(row,col) ) {
					
					// number is valid. return true if next cell is also solveable
					if(solve(row,col+1)) {
						return true;
					}
				} 
			}
			
			// if no numbers led to a solution, reset the value in the cell back to 0 and return false.
			matrix[row][col] = 0;
			return false;
		}
		
		// if the next cell is solveable, return true :)
		return solve(row,col+1);
	}

	/** 
	 * Public method to access recursionCount variable
	 * @return number of times the private solve() method has been called
	 */
	public int getCount() {
		return recursionCount;
	}
	
	/**
	 * Resets the recursion counter
	 */
	private void resetCount() {
		recursionCount = 0;
	}

	/**
	 * Check validity of number, ergo if sudoku rules allow it
	 * @param row to check
	 * @param col to check
	 * @return true if number is valid, otherwise false
	 */
	private boolean isNumberValid(int row, int col) {
		// save number
		int num = matrix[row][col];
		matrix[row][col] = 0;
		//System.out.println(""+row+col+"-"+num);
		boolean isAllowed = isRowValid(row, num) && isColumnValid(col, num) && isSquareValid(row, col, num);
		
		matrix[row][col] = num;
		
		return isAllowed;
	}
	
	/**
	 * Check if number is valid in row
	 * @param row to check
	 * @param num: number to check row for
	 * @return true if number is valid in row
	 */
	private boolean isRowValid(int row, int num) {
		for( int i = 0 ; i < size ; i++ ) {
			if( num == matrix[row][i] ) {
				//System.out.println("number already exists in row");
				return false; 
			}
		}
		return true;
	}
	
	/**
	 * Check if number is valid in column
	 * @param col: column to check
	 * @param num: number to check for in column
	 * @return true if number is valid in column
	 */
	private boolean isColumnValid(int col, int num) {
		for( int i = 0 ; i < size ; i++ ) {
			if( num == matrix[i][col] ) {
				//System.out.println("number already exists in column");
				return false; 
			}
		}
		return true;
	}

	/**
	 * Check if number is valid in square
	 * @param row that the number is located in
	 * @param col that the number is located in
	 * @param num: number to check
	 * @return true if number is valid in square
	 */
	private boolean isSquareValid(int row, int col, int num) {
		
		// find index of the first cell in the related square
		int sizeRoot = (int) Math.sqrt(size); // 3 if size is 9
		int startRow = (row/sizeRoot)*sizeRoot; // eg: 8/3=2, * 3=6.
		int startCol = (col/sizeRoot)*sizeRoot;
		
		for(int r = 0 ; r < sizeRoot ; r++) {
			for(int c = 0 ; c < sizeRoot ; c++) {
				if(matrix[startRow + r][startCol + c] == num) {
					//System.out.println("number already exists in square");
					return false; 					
				}
			}
		}	
		
		// if looped through entire square without error, the square is alright :)		
		return true;
	}
		
	/**
	 * Getter for size
	 * @return size of sudoku
	 */
	public int getSize() {
		return size;
	}
	

	/**
	 * Puts digit in the box row, col.
	 * @param row   The row [1..9]
	 * @param col   The column [1..9]
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range [1..9]
	 */
	@Override
	public void add(int row, int col, int digit) throws IllegalArgumentException {
		if(isInputAllowed(digit) && isInputAllowed(row) && isInputAllowed(col)) {
			matrix[row-1][col-1] = digit;
		} else {
			 throw new IllegalArgumentException("Row, column or digit is outside the range [1.."+this.size+"].");
		}
	}
	/**
	 * Removes number from sudoku-matrix
	 * @param row: to remove from [1..9]
	 * @param col: the column to remove from [1..9]
	 */
	@Override
	public void remove(int row, int col) {
		matrix[row-1][col-1] = 0;		
	}

	/**
	 * Get number from sudoku-matrix at position
	 * @param row: The row
	 * @param col: The column
	 * @return integer number at position
	 */
	@Override
	public int get(int row, int col) {
		return matrix[row-1][col-1];
	}

	/**
	 * Public method to check if numbers enterred into sudoku are valid
	 * @return true if the numbers are valid
	 */
	@Override
	public boolean isValid() throws IllegalArgumentException {
		
		if(checkInputs()) {
			return true;
		} else {
			throw new IllegalArgumentException("Digit outside of range");
		}
	}

	/**
	 * Clear the sudoku-matrix
	 */
	@Override
	public void clear() {
		for(int r = 0 ; r < size; r++) {
			for(int c = 0 ; c < size ; c++) {
				matrix[r][c] = 0;
			}
		}
	}

	/**
	 * Set the sudoku-matrix
	 * @param m: the matrix to set
	 */
	@Override
	public void setMatrix(int[][] m)  throws IllegalArgumentException  {
		
		if (m.length != this.size && m[0].length != this.size) {
			throw new IllegalArgumentException("Matrix has wrong dimensions");
		}
		
		for (int r = 0; r < size; r++) {
	           for (int c = 0; c < size; c++) { 
	        	   if (m[r][c] >= 0 && m[r][c] <= this.size) {
	        		   matrix[r][c] = m[r][c];
					} else {
						throw new IllegalArgumentException("Invalid input.");
					}
	           	}
			}
	}

	/**
	 * Get current sudoku-matrix
	 * @return matrix
	 */
	@Override
	public int[][] getMatrix() {
		return matrix;
	}

}

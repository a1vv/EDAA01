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
	
	@Override
	public boolean solve() {
		resetCount();
		return checkInputs() ? solve( 0, 0 ) : false;
	}
	
	// check all the inputted numbers to see if they are allowed (instead of trying to solve a clearly unsolvable sudoku)
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
	
	// return true if integer num is in range
	private boolean isInputAllowed(int num) {
		if( num > 0 && num < size) {
			return true;
		}
		return false;
	}
	
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

	public int getCount() {
		return recursionCount;
	}
	
	private void resetCount() {
		recursionCount = 0;
	}

	// return true if number is valid, otherwise false
	private boolean isNumberValid(int row, int col) {
		// save number
		int num = matrix[row][col];
		matrix[row][col] = 0;
		System.out.println(""+row+col+"-"+num);
		boolean isAllowed = isRowValid(row, num) && isColumnValid(col, num) && isSquareValid(row, col, num);
		
		matrix[row][col] = num;
		
		return isAllowed;
	}
	
	private boolean isRowValid(int row, int num) {
		for( int i = 0 ; i < size ; i++ ) {
			if( num == matrix[row][i] ) {
				System.out.println("number already exists in row");
				return false; 
			}
		}
		return true;
	}
	
	private boolean isColumnValid(int col, int num) {
		for( int i = 0 ; i < size ; i++ ) {
			if( num == matrix[i][col] ) {
				System.out.println("number already exists in column");
				return false; 
			}
		}
		return true;
	}

	
	private boolean isSquareValid(int row, int col, int num) {
		
		// find index of the first cell in the related square
		int sizeRoot = (int) Math.sqrt(size); // 3 if size is 9
		int startRow = (row/sizeRoot)*sizeRoot; // eg: 8/3=2, * 3=6.
		int startCol = (col/sizeRoot)*sizeRoot;
		
		for(int r = 0 ; r < sizeRoot ; r++) {
			for(int c = 0 ; c < sizeRoot ; c++) {
				if(matrix[startRow + r][startCol + c] == num) {
					System.out.println("number already exists in square");
					return false; 					
				}
			}
		}	
		
		// if looped through entire square without error, the square is alright :)		
		return true;
	}
		
	public int getSize() {
		return size;
	}
	

	@Override
	public void add(int row, int col, int digit) {
		matrix[row][col] = digit;
	}

	@Override
	public void remove(int row, int col) {
		matrix[row][col] = 0;		
	}

	@Override
	public int get(int row, int col) {
		return matrix[row][col];
	}

	@Override
	public boolean isValid() {
		
		return checkInputs();
	}

	@Override
	public void clear() {
		for(int r = 0 ; r < size; r++) {
			for(int c = 0 ; c < size ; c++) {
				matrix[r][c] = 0;
			}
		}
	}

	@Override
	public void setMatrix(int[][] m) {
		for (int r = 0; r < size; r++) {
	           for (int c = 0; c < size; c++) { 
	        	   matrix[r][c] = m[r][c];
	           	}
			}
	}

	@Override
	public int[][] getMatrix() {
		return matrix;
	}

}

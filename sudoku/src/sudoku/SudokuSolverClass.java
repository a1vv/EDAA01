package sudoku;

public class SudokuSolverClass implements SudokuSolver {
	
	private int[][] matrix;
	private int size = 9;
	
	public SudokuSolverClass(){
		this.matrix = new int[size][size];
	}

	@Override
	public boolean solve() {
		return solve( 0, 0 );
	}
	
	private boolean solve(int row, int col ) {
		
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
		if(matrix[row][col] == 0 ) {
			
			// loop through numbers until one works
			for(int i = 1 ; i <= size ; i++) {
				
				// set number in cell 
				matrix[row][col] = i;
				
				// check if number is allowed
				if( isNumberAllowed(row,col) ) {
					
					// number is allowed, check next cell. 					
					if(solve(row,col+1)) {
						return true;
					} else {
						// number was allowed, but did not lead to solution, try another number.
						continue;
					}
				} else { 
					// if the number was equal to size, a previous number must have been wrong. 
					if(matrix[row][col] == size) {
						return false;
					}
					// if it isn't allowed, try next number
					matrix[row][col] = 0;
				}
			}
		} 
		// 
		return true;
	}
	
	
	// return true if number is allowed, otherwise false
	private boolean isNumberAllowed(int row, int col) {
		
		// check column and row
		return isSquareAlright(row, col) && isRowAlright(row, col) && isColumnAlright(row, col);
	}
	
	private boolean isRowAlright(int row, int col) {
		int count = 0;		
		for( int i = 0 ; i < size ; i++ ) {
			if( matrix[row][col] == matrix[row][i] ) {
				count++;
				if(count > 1) return false; 
			}
		}
		return true;
	}
	
	private boolean isColumnAlright(int row, int col) {
		int count = 0;		
		for( int i = 0 ; i < size ; i++ ) {
			if( matrix[row][col] == matrix[i][col] ) {
				count++;
				if(count > 1) return false; 
			}
		}
		return true;
	}
	
	private boolean isSquareAlright(int row, int col) {
		
		// find index of the first cell in the related square
		int sizeRoot = (int) Math.sqrt(size); 
		int startRow = (row/sizeRoot)*sizeRoot;
		int startCol = (col/sizeRoot)*sizeRoot;
		
		int count = 0;
		for(int r = 0 ; r < 3 ; r++) {
			for(int c = 0 ; c < 3 ; c++) {
				System.out.println("Startrow: " + startRow + ". StartCol: " + startCol);
				if(matrix[startRow + r][startCol + c] == matrix[row][col]) {
					count++;
					if (count > 1) return false; 					
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
		
	}

	@Override
	public void remove(int row, int col) {
		
	}

	@Override
	public int get(int row, int col) {
		return 0;
	}

	@Override
	public boolean isValid() {
		
		return false;
	}

	@Override
	public void clear() {
		for(int r = 0 ; r < 9; r++) {
			for(int c = 0 ; c < 9 ; c++) {
				matrix[r][c] = 0;
			}
		}
	}

	@Override
	public void setMatrix(int[][] m) {
		for (int r = 0; r < 9; r++) {
	           for (int c = 0; c < 9; c++) { 
	        	   matrix[r][c] = m[r][c];
	           	}
			}
	}

	@Override
	public int[][] getMatrix() {
		return matrix;
	}

}

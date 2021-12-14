package sudoku;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSudoku {
	private SudokuSolverClass solver;

	@BeforeEach
	void setUp() throws Exception {
		this.solver = new SudokuSolverClass();
	}

	@AfterEach
	void tearDown() throws Exception {
		solver = null;
	}

	@Test
	void testSetAndGetMatrix() {
		 int[][] tm3 = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
					{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
					{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 0, 0, 1, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
		solver.setMatrix(tm3);
		assertArrayEquals(tm3,solver.getMatrix(),"Set matrix does not equal get matrix.");
	}
	
	@Test
	void testSolveEmptySudoku() {
		solver.clear();
		assertTrue(solver.solve(), "Couldn't solve empty sudoku.");
	}
	
	@Test
	void testSolveSudokuFromInstructions() {
		 int[][] tm3 = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
					{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
					{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 0, 0, 1, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
		solver.setMatrix(tm3);
		assertTrue(solver.solve(),"Couldn't solve sudoku from instructions-manual!");
	}
	
	@Test
	void testSolveUnsolvableSquare() {
		// matrix with two ones in the last 3x3 square
		solver.add(9,9,1);
		solver.add(8,8,1);
		assertFalse(solver.solve(),"Solved unsolvable sudoku.");
	}
	@Test
	void testSolveUnsolvableRow() {
		solver.add(1,8,1);
		solver.add(1,6,1);
		assertFalse(solver.solve(),"Solved unsolvable sudoku.");
	}
	@Test
	void testSolveUnsolvableColumn() {
		solver.add(9,1,1);
		solver.add(8,1,1);
		assertFalse(solver.solve(),"Solved unsolvable sudoku.");
	}
	
	@Test 
	void testAddAndGetNumber() {
		solver.add(3,3,3);
		assertEquals(3,solver.get(3,3),"Incorrect number at position");
		solver.add(1,1,9);
		assertEquals(9,solver.get(1,1),"Incorrect number at position");
		solver.add(9,9,9);
		assertEquals(9,solver.get(9,9),"Incorrect number at position");
		// -2 should not get added, therefore the number should still be 0
		assertThrows(IllegalArgumentException.class,() -> solver.add(7,1,-2));
		assertThrows(IllegalArgumentException.class,() -> solver.add(7,1,102));
		assertThrows(IllegalArgumentException.class,() -> solver.add(7,1,0));
		assertEquals(0,solver.get(7,1),"Incorrect number at position");
	}
	
	@Test
	void testAddAndRemoveNumber() {
		solver.add(3,3,3);
		assertEquals(3,solver.get(3,3),"Incorrect number at position");
		solver.remove(3,3);
		assertEquals(0,solver.get(3,3), "Number not removed correctly.");		
	}
	
	@Test
	void testCycleThroughPresets() {
		
	}
	
	@Test 
	void testGetCount() {
		assertEquals(0,solver.getCount());
	}
	
	@Test
	void testGetSize() {
		assertEquals(9,solver.getSize());
	}
	
	@Test
	void testIsValid() {
		solver.add(1,1,1);
		solver.add(1,9,2);
		assertTrue(solver.isValid(),"Valid inputs deemed invalid.");
		solver.add(1,2,1);
		assertThrows(IllegalArgumentException.class,() -> solver.isValid(),"Invalid inputs deemed valid.");
	}

}

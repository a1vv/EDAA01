package bst;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BSTtester {
	BinarySearchTree<Integer> bstInt;
	BinarySearchTree<String> bstString;
	BinarySearchTree<Long> bstLong;
	

	@BeforeEach
	void setUp() throws Exception {
		bstInt = new BinarySearchTree<Integer>();
		bstString = new BinarySearchTree<String>();
		bstLong = new BinarySearchTree<Long>();
	}

	@AfterEach
	void tearDown() throws Exception {
		bstInt = null;
		bstString = null;
		bstLong = null;
	}

	@Test
	void testHeighWithInt() {
		assertEquals(0, bstInt.height(), "Empty tree does not have height 0");
		bstInt.add(2);
		bstInt.add(9);
		bstInt.add(450);
		bstInt.add(5);
		assertEquals(3, bstInt.height(), "Wrong height");
	}
	
	@Test
	void testHeightWithString() {
		assertEquals(0, bstString.height(), "Empty tree does not have height 0");
		bstString.add("C");
		bstString.add("A");
		bstString.add("B");
		bstString.add("Z");
		assertEquals(3, bstString.height(), "Wrong height");
	}

	@Test
	void testAddInt() {
		bstInt.add(5);
		bstInt.add(89);
		bstInt.add(50);
		bstInt.add(4);
		assertTrue(bstInt.add(1), "Returns false even though element was added");
		assertFalse(bstInt.add(89), "Return true even though element was not added");
		assertEquals(5, bstInt.size(), "Wrong size after adding integers");
	}
	
	@Test
	void testAddString() {
		bstString.add("test");
		bstString.add("testtest");
		bstString.add("test");
		bstString.add("string");
		assertEquals(3, bstString.size(), "Wrong size after adding strings");
	}
	
	
	@Test
	void testSecondConstructor() {
		BinarySearchTree<Integer> compBst = new BinarySearchTree<Integer>((e1, e2) -> e2.compareTo(e1));
		
		compBst.add(5);
		compBst.add(89);
		compBst.add(50);
		compBst.add(4);
		assertTrue(compBst.add(1), "Returns false even though element was added");
		assertFalse(compBst.add(89), "Return true even though element was not added");
		assertEquals(5, compBst.size(), "Wrong size after adding integers");
	
	}
	
	@Test
	void testSize() {
		bstInt.add(2);
		assertEquals(1,bstInt.size(), "Wrong size after adding integer");
		bstInt.add(2);
		assertEquals(1,bstInt.size(), "Wrong size after adding integer");
		bstInt.add(20);
		assertEquals(2,bstInt.size(), "Wrong size after adding integer");
	}
	
	@Test
	void testClear() {
		bstInt.add(20);
		bstInt.add(10);
		bstInt.add(30);
		bstInt.clear();
		assertEquals(0,bstInt.size(),"Wrong size after clearing");
	}
	
}

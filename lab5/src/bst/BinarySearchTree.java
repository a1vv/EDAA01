package bst;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;


public class BinarySearchTree<E> {
  BinaryNode<E> root;  // Används också i BSTVisaulizer
  int size;            // Används också i BSTVisaulizer
  private Comparator<E> ccomparator;
  
	public static void main(String[] args) throws InterruptedException {
		BSTVisualizer visualizer = new BSTVisualizer("window", 1200, 400);
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>((e2, e1) -> ((Comparable<Integer>) e1).compareTo(e2));
		Random rand = new Random();
		
		for (int i = 0; i < 50; i++) {
			bst.add(rand.nextInt(100));
		}
		
		visualizer.drawTree(bst);
		bst.printTree();
		
		Thread.sleep(5000);
		
		bst.rebuild();
		
		visualizer.drawTree(bst);
	}
	/**
	 * Constructs an empty binary search tree.
	 */
	public BinarySearchTree() {
		this.ccomparator = (e1, e2) -> ((Comparable<E>) e1).compareTo(e2);
		root = null; 
		size = 0;
	}
	
	/**
	 * Constructs an empty binary search tree, sorted according to the specified comparator.
	 */
	public BinarySearchTree(Comparator<E> comparator) {
		this.ccomparator = comparator;
		root = null; 
		size = 0;
	}

	/**
	 * Inserts the specified element in the tree if no duplicate exists.
	 * @param x element to be inserted
	 * @return true if the the element was inserted
	 */
	public boolean add(E x) {
		
		if ( root == null ) {
			root = new BinaryNode<E>(x);
			size++;
			return true;
		}
		
		return add(root, x);
	}
	
	private boolean add(BinaryNode<E> n, E x) {
		
		int diff = ccomparator.compare(n.element, x);
		
		if (diff == 0) {
			return false; 
			
		// check left side
		} else if (diff > 0) {
			
			if (n.left == null) {
				n.left = new BinaryNode<E>(x);
				size++;
			} 
			else return add(n.left, x);
			
		// check right side
		} else {
			
			if (n.right == null) {
				n.right = new BinaryNode<E>(x);
				size++;
			} 
			else return add(n.right, x);
		}
		
		return true;
	}
	/**
	 * Computes the height of tree.
	 * @return the height of the tree
	 */
	public int height() {
		return height(this.root, 0);
	}
	
	private int height(BinaryNode<E> node, int height) {
		
		if ( node == null) {
			height = 0;
		} else {
			// check both left and right and return the largest size
			height = 1 + Math.max(height(node.left, height), height(node.right, height));
		}
		return height;
	}
	
	/**
	 * Returns the number of elements in this tree.
	 * @return the number of elements in this tree
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Removes all of the elements from this list.
	 */
	public void clear() {
		root = null;
		size = 0;
	}
	
	/**
	 * Print tree contents in inorder.
	 */
	public void printTree() {
		printTree(root);
	}

	private void printTree(BinaryNode<E> n) {
		if (n != null) {
			printTree(n.left);
			System.out.println(n.element);
			printTree(n.right);
		}
	}
	
	/** 
	 * Builds a complete tree from the elements in the tree.
	 */
	public void rebuild() {
		ArrayList<E> sorted = new ArrayList<E>();
		toArray(root, sorted);
		root = buildTree(sorted, 0, sorted.size() - 1);
	}
	
	/*
	 * Adds all elements from the tree rooted at n in inorder to the list sorted.
	 */
	private void toArray(BinaryNode<E> n, ArrayList<E> sorted) {
		if (n != null) {
			toArray(n.left, sorted);
			sorted.add(n.element);
			toArray(n.right, sorted);
		}
	}
	
	/*
	 * Builds a complete tree from the elements from position first to 
	 * last in the list sorted.
	 * Elements in the list a are assumed to be in ascending order.
	 * Returns the root of tree.
	 */
	private BinaryNode<E> buildTree(ArrayList<E> sorted, int first, int last) {
		if(first > last) {
			return null;
		}
		
		int mid = (last + first) / 2;
		
		BinaryNode<E> node = new BinaryNode<>(sorted.get(mid)); 
		
		// Build left side of sorted list
		node.left = this.buildTree(sorted, first, mid - 1);
		
		// Build right side of sorted list
		node.right = this.buildTree(sorted, mid + 1, last);
		
		return node;
	}
	


	static class BinaryNode<E> {
		E element;
		BinaryNode<E> left;
		BinaryNode<E> right;

		private BinaryNode(E element) {
			this.element = element;
		}	
	}
	
}

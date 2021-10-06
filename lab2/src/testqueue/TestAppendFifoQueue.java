package testqueue;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import queue_singlelinkedlist.FifoQueue;

class TestAppendFifoQueue {
	private FifoQueue<Integer> q1;
	private FifoQueue<Integer> q2;

	@BeforeEach
	void setUp() throws Exception {
		q1 = new FifoQueue<Integer>();
		q2 = new FifoQueue<Integer>();
	}

	@AfterEach
	void tearDown() throws Exception {
		q1 = q2 = null;
	}

	@Test
	void testTwoEmpty() {
		q1.append(q2);
		assertEquals(0,q1.size(), "Size not zero.");
	}
	
	@Test
	void testEmptyToNonempty() {
		q1.offer(5);
		q1.offer(2);
		q1.append(q2);
		assertEquals(2,q1.size(), "Size of queue changed.");
		assertEquals(5,q1.poll(), "First element is incorrect.");
		assertEquals(2,q1.peek(), "Wrong element peeked.");
	}
	
	@Test
	void testNonemptyToEmpty() {
		q2.offer(5);
		q2.offer(2);
		q1.append(q2);
		assertEquals(2,q1.size(), "Size of queue incorrect.");
		assertEquals(5,q1.poll(), "First element is incorrect.");
		assertEquals(2,q1.peek(), "Wrong element peeked.");
	}
	
	@Test
	void testTwoNonempty() {
		q1.offer(2);
		q1.offer(9);
		q1.offer(44);
		q2.offer(200);
		q2.offer(182);
		q1.append(q2);
		assertEquals(5,q1.size(),"Size of queue doesn't equal sum of queues.");
		assertEquals(2,q1.poll(), "Wrong order.");
		assertEquals(9,q1.poll(), "Wrong order.");
		assertEquals(44,q1.poll(), "Wrong order.");
	}
	
	@Test
	void testAppendToItself() {
		q1.offer(22);
		q1.offer(29);
		q1.offer(299);
		q1.offer(999);
		assertThrows(IllegalArgumentException.class,()->q1.append(q1));
	}
	
	@Test
	void testAppendedIsEmpty() {
		q1.offer(2);
		q1.offer(9);
		q1.offer(44);
		q2.offer(200);
		q2.offer(182);
		q1.append(q2);
		assertEquals(0,q2.size(),"Appended queue does not become empty.");
	}

}

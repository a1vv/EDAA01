package queue_singlelinkedlist;
import java.util.*;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}

	/**	
	 * Inserts the specified element into this queue, if possible
	 * post:	The specified element is added to the rear of this queue
	 * @param	e the element to insert
	 * @return	true if it was possible to add the element 
	 * 			to this queue, else false
	 */
	public boolean offer(E e) {
		QueueNode<E> node = new QueueNode<E>(e);
		if ( last == null ) {
			node.next = node;
		} else {
			node.next = last.next;
			last.next = node;
		}
		last = node;
		size++;
		return true;
	}
	
	/**	
	 * Returns the number of elements in this queue
	 * @return the number of elements in this queue
	 */
	public int size() {		
		return size;
	}
	
	/**	
	 * Retrieves, but does not remove, the head of this queue, 
	 * returning null if this queue is empty
	 * @return 	the head element of this queue, or null 
	 * 			if this queue is empty
	 */
	public E peek() {
		if (last != null) {
			return last.next.element;
		}
		return null;
	}

	/**	
	 * Retrieves and removes the head of this queue, 
	 * or null if this queue is empty.
	 * post:	the head of the queue is removed if it was not empty
	 * @return 	the head of this queue, or null if the queue is empty 
	 */
	public E poll() {
		if (last != null) {
			QueueNode<E> first = last.next;
			if (last == first) {
				last = null;
			} else {
				last.next = first.next;
			}
			size--;
			return first.element;
		} 		
		return null;
	}
	
	/**	
	 * Returns an iterator over the elements in this queue
	 * @return an iterator over the elements in this queue
	 */	
	public Iterator<E> iterator() {
		return new QueueIterator();
	}
	
	public void append(FifoQueue<E> q) {
		if(q==this) {
			throw new IllegalArgumentException();
		}
		// om första listan är tom ska nästa listas objekt ersätta last.
		if(last == null) {
			if(q.last != null) {
				last = q.last;
			}
		// annars ändras referenserna från this.last till q.last.next och q.last.next till this.last.next.
		} else if (q.last != null) {
			QueueNode<E> first = last.next;
			last.next = q.last.next;
			q.last.next = first;
			last = q.last; // gör last till det sista objektet i listan
		}
		// storleken har nu ökat med storleken på q, oavsett om q är tom eller ej.
		size += q.size();	
		// referenserna från q är nu överförda till aktuell lista, så q.last och q.size kan 
		// nollställas utan att några objekt går förlorade
		q.last = null;
		q.size = 0;
	}
	
	
	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}
	
	private class QueueIterator implements Iterator<E> {
		private QueueNode<E> pos;
		
		private QueueIterator() {
			if(last != null) {
				pos = last.next;
			} else {
				pos = null;
			}
		}
		
		
		// blir fel när man kommer till slutet av listan
		public boolean hasNext() {
			if (pos != null) {
				return true;
			} 
			return false;
		}
		
		public E next() {
			if(hasNext()) {
				QueueNode<E> temp = pos;
				pos = pos == last ? null : pos.next;
				return temp.element;
			} else {
				throw new NoSuchElementException();
			}
		}
	}

}

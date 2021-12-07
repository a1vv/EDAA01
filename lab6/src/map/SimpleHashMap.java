package map;

import java.util.Random;

public class SimpleHashMap<K, V> implements Map<K, V> {
	
	private Entry<K, V>[] table;
	private double loadFactor;
	
	
	public static void main(String[] args) {
		
		final int VALUES = 100;
		SimpleHashMap<Integer, Integer> shm = new SimpleHashMap<Integer, Integer>(VALUES);
		
		Random rand = new Random();
		for (int i = 0; i < VALUES; i++) {
			int nbr = rand.nextInt(1000) - 500;
			shm.put(nbr, nbr);
		}
		
		System.out.println(shm.show());
		System.out.println("Table size: " + shm.size() + ", values: " + VALUES + ", length: " + shm.table.length + ", LF: " + (double) shm.size() / shm.table.length);
		
		
	}
	
	/** Constructs an empty hashmap with the default initial capacity (16)
	and the default load factor (0.75). */
	public SimpleHashMap() {
		this(16);
	}
	
	/** Constructs an empty hashmap with the specified initial capacity
	and the default load factor (0.75). */
	public SimpleHashMap(int capacity) {		
		table = (Entry<K,V>[]) new Entry[capacity];
		this.loadFactor = 0.75;
	}
	
	public String show() {
		StringBuilder sb = new StringBuilder();
		for ( int i = 0 ; i < table.length ; i++) {
			
			sb.append(i + "\t");
		
			Entry<K, V> temp = table[i];
			while( temp != null) {
				sb.append(table[i].toString() + " ");
				temp = temp.next;
			}
			
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private int index(K key) {
		int index = key.hashCode() % table.length;
		return index < 0 ? index + table.length : index;
	}
	
	
	
	/**
	 * returnera det Entry-par som har nyckeln key i listan som finns på position index i tabellen. Om det inte finns något sådant ska metoden returnera null
	 */
	private Entry<K,V> find(int index, K key) {
		
		Entry<K, V> temp = this.table[index];

		// Loop through the entries to find the right entry
		while (temp != null) {
			if (temp.getKey().equals(key)) {
				return temp;
			}

			temp = temp.next;
		}
		
		return null;
	}

	@Override
	public V get(Object arg0) {
		K key = (K) arg0;
		Entry<K, V> entry = find(index(key),key);
		
		return entry != null ? entry.getValue() : null;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public V put(K key, V value) {

		int index = index(key);
		Entry<K, V> temp = find(index, key);
		
		V res = null;
		
		if( temp != null) {
			// save previous value before setting new one
			res = temp.getValue();
			temp.setValue(value);
		} else {
			// set value last in list on index
			
			Entry<K, V> entry = new Entry<>(key, value);

			// If index is empty
			if (table[index] == null)
				table[index] = entry;

			else {
				// Get Entry for key
				temp = table[index];

				// Find last entry
				while (temp.next != null) {
					temp = temp.next;
				}

				// Add it to the end
				temp.next = entry;
			}
		}
		
		// check if the load factor is too high, rehash if that is the case.
		double currentLoadFactor = (double) this.size() / this.table.length;
		if( currentLoadFactor > loadFactor) {
			rehash();
		}		
				
		// return previous value, if there was one.
		return res;
	}
	
	
	/**
	 * Take all entries and move to a new table with double the size.
	 */
	private void rehash() {
		
		Entry<K, V>[] oldTable = (Entry<K,V>[]) new Entry[table.length];

		for (int i = 0; i < table.length; i++) {
			oldTable[i] = table[i];
		}
		
		table =  (Entry<K,V>[]) new Entry[oldTable.length*2];
		
		
		for ( int i = 0 ; i < oldTable.length ; i++) {
		
			Entry<K, V> temp = oldTable[i];
			
			while( temp != null) {
				this.put(temp.getKey(), temp.getValue());
				// go to next value on index i
				temp = temp.next;
			}
		}
	}
	
	

	@Override
	public V remove(Object arg0) {
		
		K key = (K) arg0;
		int index = index(key);
		
		// if the list at index is empty, return null. Else:
		if(table[index] != null) {
			Entry<K,V> temp = table[index];
			
			// if the first item in the list contains the key
			if(temp.getKey().equals(key)) {
				table[index] = table[index].next; //Remove by setting next element to first in list
				return temp.getValue();
				
			// else search through rest of list to find key
			} else {
				while(temp.next != null ) {
					if (temp.next.getKey().equals(key)) {
						V result = temp.next.getValue();
						temp.next = temp.next.next;
						return result;
					}
					temp = temp.next;
				}
			}
		}
		
		return null;
	}

	@Override
	public int size() {
		int size = 0;
		
		for( int i = 0 ; i < table.length ; i++) {

			Entry<K, V> temp = table[i];
			while( temp != null) {
				size++;
				temp = temp.next;
			}
		}
		
		return size;
	}
	
	private static class Entry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;
		private Entry<K, V> next;
		
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}
		
		@Override
		public String toString() {
			return key + "=" + value;
		}
		
	}
}

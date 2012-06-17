package elementaryST;

import java.util.LinkedList;
import java.util.Queue;

import symbolTables.OrderedSymbolTable;




public class OrderedSequentialSearchST<TKey extends Comparable<TKey>, TValue> implements OrderedSymbolTable<TKey, TValue> {

	private class Node
	{
		TKey key;
		TValue value;
		Node next;
		
		public Node(TKey key, TValue value, Node next)
		{
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
	}
	
	private Node first;
	private int N = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}
	
	public void clear()
	{
		N = 0;
		first = null;
	}
	
	public Iterable<TKey> keys()
	{
		Queue<TKey> q = new LinkedList<TKey>();
		Node p = first;
		while(p != null)
		{
			q.add(p.key);
			p = p.next;
		}
		return q;
	}

	public void put(TKey key, TValue value) {
		Node prev = null;
		Node cur = first;
		while(cur != null && cur.key.compareTo(key) < 0)
		{
			prev = cur;
			cur = cur.next;
		}
			
		if(N == 0 || cur == first)
			first = new Node(key, value, first);
		else if (cur == null || cur.key.compareTo(key) > 0)
			prev.next = new Node(key, value, prev.next);
		else //cur.key == key
		{
			cur.value = value;
			return;
		}
		N++;
	}

	public TValue get(TKey key) {
		Node n = first;
		while(n != null)
			if(n.equals(key))
				return n.value;
		return null;
	}

	public void delete(TKey key) {
		if(N == 0) return;
		Node prev = null;
		Node cur = first;
		while(cur != null && cur.key.compareTo(key) < 0)
		{
			prev = cur;
			cur = cur.next;
		}
		if(cur == null) return;
		if(cur.key.compareTo(key) == 0)
		{
			prev = prev.next;
			N--;
		}
	}

	public boolean contains(TKey key) {
		return get(key) != null;
	}

	public int size() {
		return N;
	}

	public TKey min() {
		return first != null ? first.key : null;
	}

	public TKey max() {
		if(first == null) return first.key;
		Node p = first;
		while(p.next != null)
			p = p.next;
		return p.key;
	}

	public TKey floor(TKey key) {
		if(N == 0 || first.key.compareTo(key) > 0) return null;
		Node p = first;
		if(p.next != null && p.next.key.compareTo(key) < 0)
			p = p.next;
		return p.key;
	}

	public TKey ceiling(TKey key) {
		if(N == 0) return null;
		if(first.key.compareTo(key) > 0) return first.key;
		Node p = first ;
		while(p != null && p.key.compareTo(key) < 0) 
			p = p.next;
		return p == null ? null : p.key;
	}

	public TKey select(int i) {
		if(N == 0|| i >= N) return null;
		Node p = first;
		for(int j = 0 ; j < i ; j++)
			p = p.next;
		return p.key;
	}

	public int rank(TKey key) {
		if(N == 0) return 0;
		int rank = 0;
		for(Node p = first; rank < N && key.compareTo(p.key) > 0; rank++)
			p = p.next;
		return rank;
	}

	public void deleteMin() {
		if(first == null) return;
		else first = first.next;
		N--;
	}

	public void deleteMax() {
		if(first == null) return;
		if(N == 1) first = null;
		else
		{			
			Node p = first;
			for(int i = 0 ; i < N - 1 ; i++)
				p = p.next;
			if(p == first) first.next = null;
			else p.next = null;
			N--;
		}
	}

}

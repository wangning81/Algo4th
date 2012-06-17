package elementaryST;

import java.util.LinkedList;
import java.util.Queue;

import symbolTables.SymbolTable;


public class SequentialSearchST<Key, Value> implements SymbolTable<Key, Value> {
	
	private class Node
	{
		public Key Key;
		public Value Value;
		public Node Next;
		
		public Node(Key key, Value value, Node next)
		{
			Key = key;
			Value = value;
			Next = next;
		}
	}
	
	private Node first;
	private int N = 0;
	private Node cachedNode = null;
	
	public SequentialSearchST()
	{
		
	}
	
	public void clear()
	{
		first = null;
		N = 0;
		cachedNode = null;
	}
	
	public void put(Key key, Value value)
	{
		if(key == null) return; //null key
		
		//cache hit
		if(cachedNode != null && cachedNode.Key.equals(key))
		{
			cachedNode.Value = value;
			return;
		}
		
		Node node = getNode(key);
		if(node == null)
		{
			first = new Node(key, value, first);
			cachedNode = first; //update cache
			N++;
		}
		else
		{
			node.Value = value;
			cachedNode = node; //update cache
		}
	}
	
	public Value get(Key key)
	{
		if(key == null) return null; //null key
		
		if(cachedNode != null && cachedNode.Key.equals(key)) //cache hit
		{
			return cachedNode.Value;
		}
		
		Node n = getNode(key);
		if(n != null )
		{
			cachedNode = n; //update cache
			return n.Value;
		}
		return null;
	}
	
	public void delete(Key key)
	{
		if(first.Key.equals(key))
		{
			first = first.Next;
			if(first == cachedNode) //reset cache.
				cachedNode = null;
		}
		else
		{
			Node f = first;
			while(f.Next != null && !f.Next.Key.equals(key))
				f = f.Next;
			if(f.Next == null)
				return;
			
			f.Next = f.Next.Next;
			
			if(first.Next == cachedNode) //reset cache.
				cachedNode = null;		
		}
		N--;
	}
	
	public int size()
	{
		return N;
	}
	
	public boolean isEmpty()
	{
		return N == 0;
	}
	
	private Node getNode(Key key)
	{
		Node t = first;
		while(t != null && !t.Key.equals(key))
			t = t.Next;
		return t;
	}
	
	public Iterable<Key> keys()
	{
		Queue<Key> q = new LinkedList<Key>();
		Node n = first;
		while(n != null)
		{
			q.add(n.Key);
			n = n.Next;
		}
		return q;
	}
	
	public boolean contains(Key key) {
		return get(key) != null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();
		st.put("Felix Wang", 100);
		st.put("Neil", 55);
		st.put("Zirl", 77);
		
		System.out.println(st.get("Zirl"));
		System.out.println(st.get("Neil"));
		st.delete("Felix Wang");
		System.out.println(st.get("Felix Wang"));
		st.delete("Zirl");
		System.out.println(st.get("Zirl"));
	}



}

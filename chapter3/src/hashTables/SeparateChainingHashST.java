package hashTables;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import symbolTables.SymbolTable;
import symbolTables.WordFreqencyCounter;

public class SeparateChainingHashST<TKey, TValue> implements SymbolTable<TKey, TValue>{

	private static int M = 997;
	private int avgProbes = -1;
	
	private class Node
	{
		public TKey Key;
		public TValue Value;
		public Node Next;
		public int Index;
		
		public Node(TKey key, TValue value, Node next, int index)
		{
			Key = key;
			Value = value;
			Next = next;
			Index = index;
		}
	}
	
	private int N = 0;
	
	@SuppressWarnings("unchecked")
	private Node[] slots 
			= new SeparateChainingHashST.Node[M];
		
	private int hash(TKey key)
	{
		return (key.hashCode() & 0x7FFFFFFF) % M; 
	}
	
	
	public SeparateChainingHashST()
	{
		
	}
	
	public SeparateChainingHashST(int avgTolerableProbes)
	{
		if(avgTolerableProbes > 1) this.avgProbes = avgTolerableProbes;
	}
	
	public void clear()
	{
		N = 0;
		for(int i = 0 ; i < M ; i++)
			slots[i] = null;
	}

	private Node getNodeWith(Node n, TKey key)
	{
		while(n != null)
		{
			if(n.Key.equals(key))
				return n;
			n = n.Next;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void resize()
	{
		Queue<TKey> keys = new LinkedList<TKey>();
		Queue<TValue> values = new LinkedList<TValue>();
		for(int i = 0 ; i < M ; i++)
		{
			if(slots[i] != null)
			{
				Node n = slots[i];
				while(n != null)
				{
					keys.add(n.Key);
					values.add(n.Value);
					n = n.Next;
				}
			}
		}
		
		M = HashHelper.nextPrimeSize(M, true);
		slots = new SeparateChainingHashST.Node[M];
		N = 0;
				
		while(!keys.isEmpty())
		{
			this.put(keys.remove(), values.remove());
		}
		
	}
	
	public void put(TKey key, TValue value) {
		
		if(avgProbes != -1 && (1 + this.loadFactor() > avgProbes))
			resize();
		
		int h = hash(key);
		Node n = getNodeWith(slots[h], key);
		if(n != null)
		{
			n.Value = value;
			return;
		}
		else
		{
			N++;
			slots[h] = new Node(key, value, slots[h], N);
		}
	}

	public TValue get(TKey key) {
		int h = hash(key);
		Node n = getNodeWith(slots[h], key);
		return n == null ? null : n.Value;
	}

	public void delete(TKey key) {
		int h = hash(key);
		Node first = slots[h];
		if(first == null) return;
		if(first.Key.equals(key))
		{
			slots[h] = first.Next;
			N--;
			return;
		}
		else 
		{
			while(first.Next != null && !first.Next.Key.equals(key))
				first = first.Next;
			if(first.Next != null)
			{
				first.Next = first.Next.Next;
				N--;
			}
		}
	}
	
	public void deleteAllKeysWithIndexLargerThan(int k)
	{
		if( k < 0 ) return;
		
		for(int i = 0 ; i < M  ; i++)
		{
			Node n = slots[i];
			if(n != null)
			{
				//1. delete all leading nodes with index > k
				while(n != null && n.Index > k)
				{
					n = n.Next;
					N--;
				}
				slots[i] = n;
								
				//2. now n is the first node with index <= k, 
				//search for the end node with this property.
				while(n != null)
				{
					while(n.Next != null && n.Next.Index <= k)
						n = n.Next;
					//3. delete all nodes with index > k
					Node p = n.Next;
					while(p != null && p.Index > k)
					{
						p = p.Next;
						N--;
					}
					n.Next = p;
					n = p;
				}
			}
		}
	}

	public boolean contains(TKey key) {
		return get(key) != null;
	}

	public int size() {
		return N;
	}

	public Iterable<TKey> keys() {
		Queue<TKey> ret = new LinkedList<TKey>();
		for(Node first : slots)
			while(first != null)
			{
				ret.add(first.Key);
				first = first.Next;
			}
		return ret;
	}
	
	public double loadFactor()
	{
		return (1.0 * N) / M;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SymbolTable<String, Integer> st = new SeparateChainingHashST<String, Integer>(2);
		WordFreqencyCounter<SymbolTable<String, Integer>> c = new WordFreqencyCounter<SymbolTable<String, Integer>>(st);
		c.feed(new File("D:\\bible.txt"), 2);
	}

}

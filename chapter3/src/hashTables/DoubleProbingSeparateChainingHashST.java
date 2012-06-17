package hashTables;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import elementaryST.SequentialSearchST;
import symbolTables.SymbolTable;
import symbolTables.WordFreqencyCounter;

public class DoubleProbingSeparateChainingHashST<TKey, TValue> implements SymbolTable<TKey, TValue> {
	private final int M = 997;
	@SuppressWarnings("unchecked")
	private SequentialSearchST<TKey, TValue>[] slots = (SequentialSearchST<TKey, TValue>[]) new SequentialSearchST[M];
	
	public DoubleProbingSeparateChainingHashST()
	{
		
	}
	
	public int hash1(TKey key)
	{
		return (key.hashCode() & 0x7FFFFFFF) % M;		
	}
	
	public int hash2(TKey key)
	{
		return ((key.hashCode() * 37) & 0x7FFFFFFF) % M;
	}
	

	public void put(TKey key, TValue value) {
		int h1 = hash1(key);
		int h2 = hash2(key);
		int h;
		
		if(
				slots[h1] == null 
				||
				slots[h1].contains(key)
				||
				(slots[h2] != null && !slots[h2].contains(key) && slots[h1].size() <= slots[h2].size()) 
		   )
		{
			h = h1;
		}
		else 
		{ 
			h = h2;
		}
		
		if(slots[h] == null) slots[h] = new SequentialSearchST<TKey, TValue>();		
		slots[h].put(key, value);
	}

	public TValue get(TKey key) {
		int index = getSlotIndex(key);
		return index == -1 ? null : slots[index].get(key);
	}
	
	private int getSlotIndex(TKey key)
	{
		int h1 = hash1(key);
		int h2 = hash2(key);
		if(slots[h1] != null && slots[h1].get(key) != null)	 return h1;
		else if(slots[h2] != null && slots[h2].get(key) != null) return h2;
		else return -1;
	}

	public void delete(TKey key) {
		int h1 = hash1(key);
		int h2 = hash2(key);
		if(slots[h1] != null && slots[h1].contains(key))
		{
			slots[h1].delete(key);
			return;
		}
		
		if(slots[h2] != null && slots[h2].contains(key))
		{
			slots[h2].delete(key);
			return;
		}
	}

	public boolean contains(TKey key) {
		return get(key) != null;
	}

	public int size() {
		int size = 0;
		for(int i = 0 ; i < M ; i++)
			if(slots[i] != null)
				size += slots[i].size();
		return size;
	}

	public Iterable<TKey> keys() {
		Queue<TKey> q = new LinkedList<TKey>();
		for(int i = 0 ; i < M ; i++)
			if(slots[i] != null)
				for(TKey key : slots[i].keys())
					q.add(key);
		return q;
	}

	public void clear() {
		for(int i = 0 ; i < M ; i++)
			if(slots[i] != null)
				slots[i].clear();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DoubleProbingSeparateChainingHashST<String, Integer> st = new DoubleProbingSeparateChainingHashST<String, Integer>();
		WordFreqencyCounter<DoubleProbingSeparateChainingHashST<String, Integer>> c = new WordFreqencyCounter<DoubleProbingSeparateChainingHashST<String, Integer>>(st);
		c.feed(new File("D:\\bible.txt"), 2);
		//st.delete("the");
		//System.out.println(st.contains("the"));
		//st.put("1",	1);
		//st.put("2",	2);
		//st.delete("1");
		System.out.println(st.size());

	}

}

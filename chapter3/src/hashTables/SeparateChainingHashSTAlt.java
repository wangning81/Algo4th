package hashTables;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import symbolTables.SymbolTable;
import symbolTables.WordFreqencyCounter;
import elementaryST.SequentialSearchST;

public class SeparateChainingHashSTAlt<TKey, TValue> implements SymbolTable<TKey, TValue> {
	private final int M = 997;
	@SuppressWarnings("unchecked")
	private SequentialSearchST<TKey, TValue>[] slots = (SequentialSearchST<TKey, TValue>[]) new SequentialSearchST[M];
	
	public SeparateChainingHashSTAlt()
	{
		
	}
	
	public int hash(TKey key)
	{
		return (key.hashCode() & 0x7FFFFFFF) % M;		
	}
		

	public void put(TKey key, TValue value) {
		int h = hash(key);
		if(slots[h] == null) slots[h] = new SequentialSearchST<TKey, TValue>();		
		slots[h].put(key, value);
	}

	public TValue get(TKey key) {
		int h = hash(key);
		return slots[h] == null ? null : slots[h].get(key);
	}
	
	public void delete(TKey key) {
		int h = hash(key);
		if(slots[h] != null)
			slots[h].delete(key);
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
	
	public double kaiSquareStat()
	{
		double ret = 0;
		double loadFactor  = (1.0 * size()) / M;
		for(int i = 0 ; i < M ; i++)
			if(slots[i] != null)
				ret += Math.pow(slots[i].size() - loadFactor, 2);
		return ret / loadFactor;		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SeparateChainingHashSTAlt<String, Integer> st = new SeparateChainingHashSTAlt<String, Integer>();
		WordFreqencyCounter<SeparateChainingHashSTAlt<String, Integer>> c = new WordFreqencyCounter<SeparateChainingHashSTAlt<String, Integer>>(st);
		c.feed(new File("D:\\bible.txt"), 2);
		//st.delete("the");
		//System.out.println(st.contains("the"));
		//st.put("1",	1);
		//st.put("2",	2);
		//st.delete("1");
		System.out.println(st.size());
		System.out.println(st.kaiSquareStat());

	}

}

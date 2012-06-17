package hashTables;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import symbolTables.SymbolTable;
import symbolTables.WordFreqencyCounter;

public class LinearProbingHashSTLasyDeletion<TKey, TValue> implements SymbolTable<TKey, TValue> {
	private TKey[] keys;
	private TValue[] values;
	private int M = 31;
	private int N = 0;
	private int tombStones = 0;
		
	@SuppressWarnings("unchecked")
	public LinearProbingHashSTLasyDeletion()
	{
		keys = (TKey[])new Object[M];
		values = (TValue[])new Object[M];		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinearProbingHashSTLasyDeletion<String, Integer> st = new LinearProbingHashSTLasyDeletion<String, Integer>();
		WordFreqencyCounter<LinearProbingHashSTLasyDeletion<String, Integer>> c = new WordFreqencyCounter<LinearProbingHashSTLasyDeletion<String, Integer>>(st);
		c.feed(new File("D:\\bible.txt"), 2);
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int newSize)
	{
		TKey[] oldKeys = keys;
		TValue[] oldValues = values;
		keys = (TKey[])new Object[newSize];
		values = (TValue[])new Object[newSize];
		
		int n = N;
		int m = M;
		
		M = newSize;
		N = 0;
		tombStones = 0;
				
		for(int i = 0, k = 0 ; i < n && k < m ; k++)
		{
			if(oldKeys[k] != null && oldValues[k] !=null)
			{
				put(oldKeys[k], oldValues[k]);
				i++;
			}
		}
	}
	
	private int hash(TKey key)
	{
		return (key.hashCode() & 0x7FFFFFFF) % M ;
	}

	public void put(TKey key, TValue value) {
		if(N + tombStones == M / 2)
			resize(M * 2);
		int h = hash(key);
		int i = h;
		
		while(keys[i] != null )
		{
			if(keys[i].equals(key))
			{
				if(values[i] == null) 
				{
					tombStones--;
					N++;
				}
				values[i] = value;
				return;
			}
			else
			{
				i = (i + 1) % M;
			}
		}
		
		//insert;
		keys[i] = key;
		values[i] = value;
		N++;
	}

	public TValue get(TKey key) {
		int h = hash(key);
		for( 
				int i = h;
				keys[i] != null ;
				i = (i + 1) % M
			)
		{
			if(keys[i].equals(key))
			{
				return values[i];
			}
		}
		return null;
	}

	public void delete(TKey key) {
		int h = hash(key);
		int i = h; 
		while(keys[i] != null && !keys[i].equals(key))
			i = (i+1) % M;
		if(keys[i] == null) //not found
			return;
		
		values[i] = null;
		N--;
		tombStones++;
		
		if(N <= M / 8) resize(M/2);
		
	}

	public boolean contains(TKey key) {
		return get(key) != null;
	}

	public int size() {
		return N;
	}
	
	public Iterable<TKey> keys() {
		Queue<TKey> q = new LinkedList<TKey>();
		for(int i = 0, j = 0 ; i < N ; j++)
			if(keys[j] != null && values[j] != null)
			{
				q.add(keys[j]);
				i++;
			}
		return q;		
	}

	@SuppressWarnings("unchecked")
	public void clear() {
		M = 31;
		N = 0;
		tombStones = 0;
		keys = (TKey[])new Object[M];
		values = (TValue[])new Object[M];
	}
	
	public double loadFactor()
	{
		return 1.0 * N / M;
	}

}

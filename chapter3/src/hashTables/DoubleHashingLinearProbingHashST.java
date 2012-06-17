package hashTables;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import symbolTables.SymbolTable;
import symbolTables.WordFreqencyCounter;

public class DoubleHashingLinearProbingHashST<TKey, TValue> implements SymbolTable<TKey, TValue>{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DoubleHashingLinearProbingHashST<String, Integer> st = new DoubleHashingLinearProbingHashST<String, Integer>();
		WordFreqencyCounter<DoubleHashingLinearProbingHashST<String, Integer>> c = new WordFreqencyCounter<DoubleHashingLinearProbingHashST<String, Integer>>(st);
		c.feed(new File("D:\\bible.txt"), 2);
	
	}
	
	private TKey[] keys;
	private TValue[] values;
	private final int MIN_SIZE = 31;
	private int M = MIN_SIZE;
	private int N = 0;
	private int tombStones = 0;
	
	@SuppressWarnings("unchecked")
	public DoubleHashingLinearProbingHashST()
	{
		keys = (TKey[])new Object[M];
		values = (TValue[])new Object[M];		
	}
	
	@SuppressWarnings("unchecked")
	private void resize(boolean isIncrease)
	{
		//do not decrease size when it is min size;
		if(M == MIN_SIZE && !isIncrease)
			return;
		
		TKey[] oldKeys = keys;
		TValue[] oldValues = values;
		
		int newSize = HashHelper.nextPrimeSize(M, isIncrease);
		
		keys = (TKey[])new Object[newSize];
		values = (TValue[])new Object[newSize];
		
		int n = N;
		int m = M;
		
		M = newSize;
		N = 0;
		tombStones = 0;
				
		for(int i = 0, k = 0 ; i < n && k < m ; k++)
		{
			if(oldKeys[k] != null && oldValues[k] != null)
			{
				put(oldKeys[k], oldValues[k]);
				i++;
			}
		}
	}
	
	private int hash1(TKey key)
	{
		return (key.hashCode() & 0x7FFFFFFF) % M ;
	}
	
	private int hash2(TKey key)
	{
		int h = ((key.hashCode() * 3) & 0x7FFFFFFF) % M ;
		return h != 0 ? h : h+1;
	}

	public void put(TKey key, TValue value) {
		if(N == M / 2)
			resize(true);
		
		int h = hash1(key);
		int i = h;
		int h2 = -1;
		
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
				if(h2 == -1) //first, hash it again use another hash function.
					h2 = hash2(keys[i]);
				else
					h2 = 1;				
				i = (i + h2) % M;
			}
		}
		
		//insert;
		keys[i] = key;
		values[i] = value;
		N++;
	}

	public TValue get(TKey key) {
		int h = hash1(key);
		int h2 = -1;	
		for( 
				int i = h;
				keys[i] != null ;
				i = (i + h2) % M
			)
		{
			if(keys[i].equals(key))
			{
				return values[i];
			}
			if(h2 == -1)
				h2 = hash2(keys[i]);
			else
				h2 = 1;
		}
		return null;
	}

	public void delete(TKey key) {
		int h = hash1(key);
		int h2 = -1;
		int i = h; 
		while(keys[i] != null && !keys[i].equals(key))
		{
			if(h2 == -1)
				h2 = hash2(keys[i]);
			else
				h2 = 1;
			
			i = (i+h2) % M;
		}
		if(keys[i] == null) //not found
			return;
		
		values[i] = null;
		N--;
		tombStones++;
		if(N <= M / 8 && M > MIN_SIZE) resize(false);
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
		M = MIN_SIZE;
		N = 0;
		keys = (TKey[])new Object[M];
		values = (TValue[])new Object[M];
	}
	
	public double loadFactor()
	{
		return 1.0 * N / M;
	}	

}

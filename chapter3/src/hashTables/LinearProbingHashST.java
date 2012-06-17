package hashTables;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import symbolTables.SymbolTable;
import symbolTables.WordFreqencyCounter;

public class LinearProbingHashST<TKey, TValue> implements SymbolTable<TKey, TValue>{

	private TKey[] keys;
	private TValue[] values;
	private final int MIN_SIZE = 31;
	private int M = MIN_SIZE;
	private int N = 0;
	private int totalInsertionProbes = 0;
	
	@SuppressWarnings("unchecked")
	public LinearProbingHashST()
	{
		keys = (TKey[])new Object[M];
		values = (TValue[])new Object[M];		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>();
		WordFreqencyCounter<LinearProbingHashST<String, Integer>> c = new WordFreqencyCounter<LinearProbingHashST<String, Integer>>(st);
		c.feed(new File("D:\\bible.txt"), 2);
		System.out.println("Est1 = " + st.avgSearchHitPrb());
		System.out.println("Est2 = " + st.avgSearchHitPrbAlt());
		System.out.println("Actual = " + st.avgSearchHitProbes());
		System.out.println("Est1 = " + st.avgSearchMissPrb());
		System.out.println("Est2 = " + st.avgSearchMissPrbAlt());
		System.out.println("Actual = " + st.avgSearchMissProbes());

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
		totalInsertionProbes = 0;
		
		for(int i = 0, k = 0 ; i < n && k < m ; k++)
		{
			if(oldKeys[k] != null)
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
		if(N == M / 2)
			resize(true);
		int h = hash(key);
		int i = h;
		
		int potentialProbe = 0;
		while(keys[i] != null )
		{
			if(keys[i].equals(key))
			{
				values[i] = value;
				return;
			}
			else
			{
				potentialProbe++;
				i = (i + 1) % M;
			}
		}
		
		//insert;
		totalInsertionProbes += potentialProbe + 1;
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
		
		totalInsertionProbes -= i - h + 1;
		
		keys[i] = null;
		values[i] = null;
		N--;
		
		//fix following keys
		
		for(i = (i + 1) % M; keys[i] != null ; i = (i + 1) % M)
		{
			TKey k = keys[i];
			TValue v = values[i];
			
			h = hash(k);
			totalInsertionProbes -= i - h + 1;
			
			keys[i] = null;
			values[i] = null;
			N--;
			put(k,v);
		}
		
		if(N <= M / 8 && M > MIN_SIZE) resize(false);
		
	}

	public boolean contains(TKey key) {
		return get(key) != null;
	}

	public int size() {
		return N;
	}
	
	public double avgSearchHitProbes()
	{
		return N != 0 ? 1.0 * totalInsertionProbes / N : 0; 
	}
	
	public double avgSearchMissProbes()
	{
		int sumOfClusterSqr = 0;
		//find the start point of last clustering (if any)
		int e = M - 1;
		while(keys[e] != null)
			e--;
		
		
		for(int i = (e + 1) % M ; i < e ; i = (i + 1) % M)
		{
			int clusteringLen = 0;
			for(int j = i ; i < e && keys[j] != null ; j = (j+1) % M, i = j)
				clusteringLen++;
			sumOfClusterSqr += clusteringLen * clusteringLen;
		}
		
		return 1 + loadFactor() / 2 + 1.0 * sumOfClusterSqr / (2*M);
	}

	public Iterable<TKey> keys() {
		Queue<TKey> q = new LinkedList<TKey>();
		for(int i = 0, j = 0 ; i < N ; j++)
			if(keys[j] != null)
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
	
	public double avgSearchHitPrb()
	{
		return 0.5 * (1 + 1 / (1-loadFactor()));
	}
	
	public double avgSearchHitPrbAlt()
	{
		double alpha = loadFactor();
		return (-1.0 / alpha) * Math.log(1 - alpha);
	}
	public double avgSearchMissPrb()
	{
		return 0.5 * (1 + 1 / Math.pow((1-loadFactor()),2));
	}
	
	public double avgSearchMissPrbAlt()
	{
		return 1.0 /(1- loadFactor()); 
	}
	
	public double loadFactor()
	{
		return 1.0 * N / M;
	}

}

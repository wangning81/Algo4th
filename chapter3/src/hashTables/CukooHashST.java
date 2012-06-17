package hashTables;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import symbolTables.SymbolTable;
import symbolTables.WordFreqencyCounter;



public class CukooHashST<TKey, TValue> implements SymbolTable<TKey, TValue>{

	private HashSet<TKey> hashTrace = new HashSet<TKey>();
	
	interface HashProvider<TKey>
	{
		int hash(TKey key, int M);
	}
	
	private class KeyValuePair
	{
		public TKey key;
		public TValue value;
		public KeyValuePair(TKey key, TValue value)
		{
			this.key = key;
			this.value = value;
		}
	}
	
	private class CukooTable
	{
		private TKey[] keys;
		private TValue[] values;
		private int M = 0;
		private int N = 0;
		private int tombs = 0;
		private HashProvider<TKey> hp;
		@SuppressWarnings("unchecked")
		public CukooTable(int cap, HashProvider<TKey> p)
		{
			M = cap;
			hp = p;
			keys = (TKey[])(new Object[M]); 
			values = (TValue[])(new Object[M]);
		}
		
		public KeyValuePair put(TKey key, TValue value)
		{
			if(N == M/2) resize();
			
			int h = hp.hash(key, M);
			KeyValuePair ret = null; 
			if(keys[h] != null)
			{	
				if(keys[h].equals(key)) //updating
				{
					if(values[h] == null)
					{ tombs--; N++;}
					values[h] = value;
					return null;
				}
				else
				{
					ret = new KeyValuePair(keys[h], values[h]);
					keys[h] = key;
					values[h] = value;
					return ret;
				}
			}
			
			N++;
			keys[h] = key;
			values[h] = value;
			
			return null;
		}
		
		public boolean delete(TKey key)
		{
			int h = hp.hash(key, M);
			if(keys[h] != null)
			{
				values[h] = null;
				N--;
				tombs++;
				return true;
			}
			return false;
		}
		
		@SuppressWarnings("unchecked")
		private void resize()
		{
			TKey[] oldKeys = keys;
			TValue[] oldValues = values;
			
			M *= 2;
			N = 0;
			keys = (TKey[])(new Object[M]);
			values = (TValue[])(new Object[M]);
			
			for(int i = 0 ; i < oldKeys.length ; i++)
			{
				if(oldValues[i] != null)
					put(oldKeys[i], oldValues[i]);
			}
			
			tombs = 0;
		}
		@SuppressWarnings("unused")
		public int size()
		{
			return N;
		}
		
		public Queue<KeyValuePair> getAllEntry()
		{
			Queue<KeyValuePair> q = new LinkedList<KeyValuePair>();
			for(int i = 0 ; i < M ; i++)
				if(keys[i] != null && values[i] != null)
					q.add(new KeyValuePair(keys[i], values[i]));
			return q;
		}
		
		public Queue<TKey> keys()
		{
			Queue<TKey> q = new LinkedList<TKey>();
			for(int i = 0 ; i < M ; i++)
				if(keys[i] != null && values[i] != null)
					q.add(keys[i]);
			return q;			
		}
		
		public void clear()
		{
			for(int i = 0 ; i < M ; i++)
			{
				keys[i] = null;
				values[i] = null;
			}
			N = 0;
			tombs = 0;
		}
		
		public TValue get(TKey key)
		{
			int h = hp.hash(key, M);
			return values[h];
		}
		
		public boolean contains(TKey key)
		{
			return get(key) != null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private CukooTable[] tables = new CukooHashST.CukooTable[2];
	
	private class Hasher1 implements HashProvider<TKey>
	{
		public int hash(TKey key, int M) {
			return (key.hashCode() & 0x7FFFFFFF) % M;
		}
	}
	
	private Hasher1 hasher1 = new Hasher1();
	
	private class Hasher2 implements HashProvider<TKey>
	{
		public int hash(TKey key, int M) {
			return ((key.hashCode() * 37) & 0x7FFFFFFF) % M;
		}
	}
	
	private Hasher2 hasher2 = new Hasher2();
		
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CukooHashST()
	{
		tables[0] = new CukooHashST.CukooTable(31, hasher1);
		tables[1] = new CukooHashST.CukooTable(127, hasher2);
	}

	public void put(TKey key, TValue value) {
		
		int i = 0;
		KeyValuePair p = new KeyValuePair(key, value);
		boolean rebuiltRequired = false;
		
		while(true)
		{
			p = tables[i].put(p.key, p.value);
			if(p == null) break;
			assert(hashTrace.size() == 0);
			if(hashTrace.contains(p.key))
			{
				rebuiltRequired = true;
				break;
			}	
			else
			{
				hashTrace.put(p.key);
			}
			
			i = (i+1) % 2;
		}
		hashTrace.clear();
		if(rebuiltRequired)
		{
			rebuildWith(key, value);
		}
	}
	
	private boolean putWithoutRebuild(TKey key, TValue value)
	{
		KeyValuePair p = new KeyValuePair(key, value);
		int i = 0;
					
		while(true)
		{
			p = tables[i].put(p.key, p.value);
			if(p == null) break;
			assert(hashTrace.size() == 0);
			if(hashTrace.contains(p.key))
			{
				return false;
			}	
			else
			{
				hashTrace.put(p.key);
			}
		
			i = (i+1) % 2;
		}
		hashTrace.clear();
		return true;
	}
	
	private void rebuildWith(TKey key, TValue value)
	{
		Queue<KeyValuePair> entries1 = tables[0].getAllEntry();
		Queue<KeyValuePair> entries2 = tables[1].getAllEntry();
		entries1.add(new KeyValuePair(key, value));
		tables[0].clear();
		tables[1].clear();
		for(KeyValuePair p : entries1)
		{
			if(!putWithoutRebuild(p.key, p.value))
			/*{
				System.err.println("Table cannot be rebuilt! All failed, we're done.");
				return;
			}*/
				;
		}
		for(KeyValuePair p : entries2)
		{
			if(!putWithoutRebuild(p.key, p.value))
			/*{
				System.err.println("Table cannot be rebuilt! All failed, we're done.");
				return;
			}*/
				;
		}
	}

	public TValue get(TKey key) {
		TValue v = tables[0].get(key);
		if(v != null) return v;
		else return tables[1].get(key);
	}

	public void delete(TKey key) {
		if(!tables[0].delete(key))
			tables[1].delete(key);				
	}

	public boolean contains(TKey key) {
		return tables[0].contains(key) || tables[1].contains(key);
	}

	public int size() {
		return tables[0].N + tables[1].N;
	}

	public Iterable<TKey> keys() {
		Queue<TKey> q = tables[0].keys();
		q.addAll(tables[1].keys());
		return q;
	}

	public void clear() {
		tables[0].clear();
		tables[1].clear();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CukooHashST<String, Integer> st = new CukooHashST<String, Integer>();
		WordFreqencyCounter<SymbolTable<String, Integer>> c = new WordFreqencyCounter<SymbolTable<String, Integer>>(st);
		c.feed(new File("D:\\bible.txt"), 2);
		
		/*CukooHashST<String, Integer> st = new CukooHashST<String, Integer>();
		st.put("saying", 1);
		st.put("where", 1);
		st.put("name", 1);
		st.put("alone", 1);
		st.put("leave", 1);
		st.put("saying", 1);
		*/
		
		
		

	}

}

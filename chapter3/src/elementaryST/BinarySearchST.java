package elementaryST;

import symbolTables.OrderedSymbolTable;

public class BinarySearchST <Key extends Comparable<Key>, Value> 
	extends ArrayST<Key, Value> implements OrderedSymbolTable<Key, Value>
{
	protected int cachedIndex = -1;
	
	@SuppressWarnings("unchecked")
	public BinarySearchST()
	{
		keys = (Key[])new Comparable[capicity];
		values = (Value[])new Object[capicity];
	}
	
	@Override
	public void put(Key key, Value value)
	{
		if(N == capicity)
			resize(2 * capicity);
		
		if(N == 0 || key.compareTo(keys[N - 1]) > 0)
		{
			keys[N] = key;
			values[N] = value;
			cachedIndex = N;
			N++;
			return;
		}
		int k = rank(key);
		cachedIndex = k;
		if(k < N && keys[k].equals(key))
		{
			values[k] = value;
		}
		else
		{
			for(int i = N - 1 ; i >= k ; i--)
			{
				keys[i + 1] = keys[i];
				values[i + 1] = values[i];
			}
			keys[k] = key;
			values[k] = value;
			N++;
		}
	}
	
	@Override
	public Value get(Key key)
	{
		if(cachedIndex != -1 && key.equals(keys[cachedIndex]))
			return values[cachedIndex];
		int k = rank(key);
		if(k < N && keys[k].equals(key))
		{
			cachedIndex = k;
			return values[k];
		}
		else
			return null;
	}
	
	public boolean contains(Key key)
	{
		return get(key) != null;
	}
	
	public Key min()
	{
		return N > 0 ? keys[0] : null;		
	}
	
	public Key max()
	{
		return N > 0 ? keys[N - 1] : null;
	}
	
	public Key floor(Key key)
	{
		int k = rank(key);
		if(keys[k].equals(key)) return keys[k];
		else if(k > 0) return keys[k - 1];
		else return null;
	}
	
	public Key ceiling(Key key)
	{
		int k = rank(key);
		return keys[k];
	}
	
	public int rank(Key k)
	{
		int low = 0, high = N - 1;
		while(low <= high)
		{
			int mid = low + (high - low) / 2;
			if(keys[mid].compareTo(k) > 0) high = mid -1;
			else if(keys[mid].compareTo(k) < 0) low = mid + 1;
			else return mid;
		}
		return low;		
	}
	
	public Key select(int k)
	{
		return k < N ? keys[k] : null;		
	}
	
	@Override
	public void delete(Key key)
	{
		int k = 0;
		if(cachedIndex != -1 && key.equals(keys[cachedIndex]))
		{
			k = cachedIndex;
			cachedIndex = -1;
		}
		else
			k = rank(key);
		
		if(keys[k].equals(key))
		{
			for(int i = k ; i < N - 1 ; i++)
			{
				keys[i] = keys[i + 1];
				values[i] = values[i+1];
			}
			N--;
			
			if(N < capicity / 4)
				resize(capicity / 2);
		}
	}
	
	public void deleteMin() {
		for(int i = 1 ; i < N ; i++ )
		{
			keys[i - 1] = keys[i];
			values[i - 1] = values[i];
		}
		keys[N - 1] = null;
		values[N - 1] = null;
		N--;
	}

	public void deleteMax() {
		keys[N - 1] = null;
		values[N - 1] = null;
		N--;		
	}
	
	public static <TKey extends Comparable<TKey>, TValue> boolean isValid(BinarySearchST<TKey, TValue> bsst)
	{
		for(int i = 0 ; i < bsst.N ; i++)
		{
			if( i < bsst.N - 1)
				if( bsst.keys[i].compareTo(bsst.keys[i+1]) > 0)
					return false;
			if(i != bsst.rank(bsst.select(i)))
					return false;
		}
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BinarySearchST<String, Double> st = new BinarySearchST<String, Double>();
		
		System.out.println(BinarySearchST.isValid(st));
		st.put("A+", 4.33);
		System.out.println(BinarySearchST.isValid(st));
		st.put("A", 4.00);
		System.out.println(BinarySearchST.isValid(st));
		st.put("A-", 3.67);
		System.out.println(BinarySearchST.isValid(st));
		st.put("B+", 3.33);
		System.out.println(BinarySearchST.isValid(st));
		st.put("B", 3.00);
		System.out.println(BinarySearchST.isValid(st));
		st.put("B-", 2.67);
		System.out.println(BinarySearchST.isValid(st));
		st.delete("B-");
		System.out.println(BinarySearchST.isValid(st));
		st.put("C+", 2.33);
		System.out.println(BinarySearchST.isValid(st));
		st.put("C", 2.00);
		System.out.println(BinarySearchST.isValid(st));
		st.delete("B-");
		System.out.println(BinarySearchST.isValid(st));
		st.put("C-", 1.67);
		System.out.println(BinarySearchST.isValid(st));
		st.put("D", 1.00);
		System.out.println(BinarySearchST.isValid(st));
		st.delete("A-");
		System.out.println(BinarySearchST.isValid(st));
		st.put("F", 0.00);
		
		System.out.println(BinarySearchST.isValid(st));
	}

	
	
}
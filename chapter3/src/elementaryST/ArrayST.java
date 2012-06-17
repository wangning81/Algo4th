package elementaryST;
import java.util.LinkedList;
import java.util.Queue;

import symbolTables.SymbolTable;


public class ArrayST <TKey, TValue> implements SymbolTable<TKey, TValue> {

	protected TKey[] keys;
	protected TValue[] values;
	protected int N = 0;
	protected int capicity = 16;
	
	@SuppressWarnings("unchecked")
	public ArrayST()
	{
		keys = (TKey[])new Object[capicity]; 
		values = (TValue[])new Object[capicity];
	}
	
	@SuppressWarnings("unchecked")
	public ArrayST(int capicity)
	{
		this.capicity = capicity;
		keys = (TKey[])new Object[capicity]; 
		values = (TValue[])new Object[capicity];
	}
	
	@SuppressWarnings("unchecked")
	public void clear()
	{
		capicity= 16;
		keys = (TKey[])new Object[capicity]; 
		values = (TValue[])new Object[capicity];
		N = 0;
	}

	public void put(TKey key, TValue value) {
		//double the size
		if(N == capicity)
			resize(2 * capicity);
		
		//found.
		for(int i = 0 ; i < N ; i++)
		{
			if(keys[i].equals(key))
			{
				values[i] = value;
				moveToFront(i);
				return;
			}
		}
		
		//not found, insert;
		keys[N] = key;
		values[N] = value;
		N++;
	}
	@SuppressWarnings("unchecked")
	protected void resize(int newSize)
	{
		TKey[] newKeys = (TKey[])new Object[newSize];
		TValue[] newValues = (TValue[])new Object[newSize];
		
		for(int i = 0 ; i < N ; i++){
			newKeys[i] = keys[i];
			newValues[i] = values[i];
		}
		
		keys = newKeys;
		values = newValues;
		capicity = newSize;		
	}

	public TValue get(TKey key) {
		for(int i = 0 ; i < N ; i++)
			if(keys[i].equals(key))
			{
				moveToFront(i);
				return values[0];
			}
		return null;
	}
	
	private void moveToFront(int pos)
	{
		TKey key = keys[pos];
		TValue value = values[pos];
		for(int k = pos - 1 ; k >= 0 ; k--)
		{
			keys[k + 1] = keys[k];
			values[k + 1] = values[k];
		}
		keys[0] = key;
		values[0] = value;
	}

	public void delete(TKey key) {
		int i = 0;
		for( ; i < N && !keys[i].equals(key); i++)
			;
		if(i == N) return;
		while(i + 1 < N)
		{
			keys[i] = keys[i + 1];
			values[i] = values[i + 1];
			i++;
		}
		N--;
		if(N < capicity / 4)
			resize(capicity / 2);
	}

	public boolean contains(TKey key) {
		return get(key) != null;
	}

	public int size() {
		return N;
	}
	
	public Iterable<TKey> keys()
	{
		Queue<TKey> q = new LinkedList<TKey>();
		for(TKey key : keys)
			q.add(key);
		return q;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayST<String, Integer> at = new ArrayST<String, Integer>(1);
		at.put("1", 1);
		at.put("2", 2);
		at.put("2", 2);
		at.put("2", 2);
		at.put("5", 5);
		at.put("0", 0);
		System.out.println(at.size());
		at.delete("2");
		
		System.out.println(at.get("1"));
		System.out.println(at.size());

	}

}

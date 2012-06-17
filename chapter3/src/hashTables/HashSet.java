package hashTables;

public class HashSet<T>{
	
	private static final int MIN_SIZE = 31;
	private T[] items;
	private int M = MIN_SIZE;
	private int N = 0;
	
	@SuppressWarnings("unchecked")
	public HashSet()
	{
		items = (T[])new Object[MIN_SIZE];		
	}
	
	private int hash(T item)
	{
		return (item.hashCode() & 0x7FFFFFFF) % M;
	}
	
	@SuppressWarnings("unchecked")
	private void resize(boolean isIncrease)
	{
		if(!isIncrease && M == MIN_SIZE) return;
		
		T[] oldItem = items;
		M = HashHelper.nextPrimeSize(M, isIncrease);
		N = 0;
		items = (T[])new Object[M];
		
		for(T t : oldItem)
		{
			if(t != null)
				put(t);
		}
	}
	
	public void put(T item)
	{
		if(N == M / 2) resize(true);
		
		int h = hash(item);
		while(items[h] != null && !items[h].equals(item))
			h = (h + 1) % M;
		if(items[h] == null)
		{
			items[h] = item;
			N++;
		}
		return;
	}
	
	public void delete(T item)
	{
		int h = hash(item);
		while(items[h] != null && !items[h].equals(item))
			h = (h + 1) % M;
		if(items[h] == null)
			return;
		
		items[h] = null;
		N--;
		
		for(int i = (h + 1) % M ; items[i] != null ; i = (i + 1) % M)
		{
			T t = items[i];
			items[i] = null;
			N--;
			put(t);
		}
		
		if(N <= M / 8 && M > MIN_SIZE) resize(false);
	}
	
	public boolean contains(T item)
	{
		int h = hash(item);
		while(items[h] != null && !items[h].equals(item))
			h = (h + 1) % M;
		return items[h] != null;
	}
	
	public int size()
	{
		return N;
	}
	
	@SuppressWarnings("unchecked")
	public void clear()
	{
		M = MIN_SIZE;
		items = (T[])new Object[M];
		N = 0;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashSet<Integer> iset = new HashSet<Integer>();
		iset.put(1);
		iset.put(2);
		iset.put(1);
		iset.put(3);
		
		System.out.println(iset.contains(4));
		System.out.println(iset.contains(1));
		System.out.println(iset.contains(2));
		System.out.println(iset.contains(3));

	}

}

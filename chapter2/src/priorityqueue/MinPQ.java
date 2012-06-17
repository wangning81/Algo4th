package priorityqueue;

public class MinPQ<T extends Comparable<T>> {
	
	private static final int DEFAULT_CAPICITY = 16; 
	private int capicity;
	private int size = 0;
	private T[] theHeap;
	
	public MinPQ()
	{
		this(DEFAULT_CAPICITY);
	}
	
	@SuppressWarnings("unchecked")
	public MinPQ(int initCapicity)
	{
		this.capicity = initCapicity;
		theHeap = (T[])new Comparable[capicity + 1]; 
	}
	
	public void enqueue(T item)
	{
		if(size == capicity)
			resize(2 * size);
		
		theHeap[++size] = item;
		fastSwim(size);
	}
	
	public T dequeue()
	{
		T ret = theHeap[1];
		theHeap[1] = theHeap[size--];
		sink(1);
		
		if(size < capicity / 4)
			resize(capicity / 2);		
		
		return ret;
	}
	
	private void sink(int k)
	{
		T item = theHeap[k];
		while(k <= size / 2)
		{
			int j = k * 2;
			
			if(j < size && theHeap[j].compareTo(theHeap[j + 1]) > 0)
				j += 1;
			
			if(theHeap[j].compareTo(item) < 0)
			{
				theHeap[k] = theHeap[j];
				k = j;
			}
			else
				break;
		}
		theHeap[k] = item;
	}
	
	@SuppressWarnings("unused")
	private void swim(int k)
	{
		T item = theHeap[k];
		while(k > 1 && item.compareTo(theHeap[k/2]) < 0)
		{
			theHeap[k] = theHeap[k/2];
			k /= 2;
		}
		theHeap[k] = item;
	}
	
	private void fastSwim(int k)
	{
		T item = theHeap[k];
		//initial: low = k's direct parent, high = the root;
		int low = 1, high = floorLg(k); 	
		while(low <= high)
		{
			int mid = low + (high - low)/2;
			T midItem = theHeap[k / ( 1<< mid)];
			int cmp = item.compareTo(midItem);
			if(cmp < 0)
				low = mid + 1;
			else
				high = mid - 1;
		}
		int insertPos = k / (1<< (low - 1));
		while(k > insertPos)
		{
			theHeap[k] = theHeap[k / 2];
			k /= 2;
		}
		theHeap[k] = item;
	}
	
	private int floorLg(int k)
	{
		int logK = 0;
		while(k > 1)
		{
			logK++;
			k /= 2;
		}
		return logK;			
	}
	
	private void resize(int newCapicity)
	{
		capicity = newCapicity;
		@SuppressWarnings("unchecked")
		T[] newHeap = (T[])new Comparable[capicity + 1];
		
		for(int i = 1 ; i <= size ; i++)
			newHeap[i] = theHeap[i];			
		
		theHeap =  newHeap;
	}
	
	public int size()
	{
		return size;	
	}
	
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	public T peek()
	{
		return size > 0 ? theHeap[1] : null;
	}
	
	public static void main(String[] args) {
		MinPQ<Integer> q = new MinPQ<Integer>(2);
		q.enqueue(1);
		q.enqueue(3);
		q.enqueue(7);
		q.enqueue(9);
		q.enqueue(2);
		q.enqueue(4);
		
		//q.dequeue();
		
		while(!q.isEmpty())
		{
			System.out.println(q.dequeue());			
		}
	}

}

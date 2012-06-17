package priorityqueue;

public class MaxPQ <T extends Comparable<T>>  {

	private T[] theHeap;
	private int capicity = 16;
	private int size = 0;
	
	@SuppressWarnings("unchecked")
	public MaxPQ()
	{
		theHeap = (T[])new Comparable[capicity + 1];		
	}
	
	@SuppressWarnings("unchecked")
	public MaxPQ(int capicity)
	{
		this.capicity = capicity;
		theHeap = (T[])new Comparable[capicity + 1];
	}
	
	@SuppressWarnings("unused")
	private void swim(int k)
	{
		//does not reach the root and larger than its father.
		while(k > 1 && theHeap[k].compareTo(theHeap[k/2]) > 0)
		{
			exch(k, k/2);
			k /= 2;
		}
	}
	
	private void swimWithoutExch(int k)
	{
		T item = theHeap[k];
		//does not reach the root and larger than its father.
		while(k > 1 && item.compareTo(theHeap[k/2]) > 0)
		{
			theHeap[k] = theHeap[k/2]; //pull down its father.
			k /= 2;
		}
		theHeap[k] = item;
	}
	
	@SuppressWarnings("unused")
	private void sink(int k)
	{
		//while it's not the leaf.
		while(k <= size / 2 )
		{
			int j = 2 * k;
			
			if(j < size && theHeap[j + 1].compareTo(theHeap[j]) > 0)
				j += 1;
			
			if(theHeap[j].compareTo(theHeap[k]) > 0)
			{
				exch(j, k);
				k = j;
			}
			else break;
		}
	}
	
	private void sinkWithoutExch(int k)
	{
		T item = theHeap[k];
		//while it's not the leaf.
		while(k <= size / 2 )
		{
			int j = 2 * k;
			
			if(j < size && theHeap[j + 1].compareTo(theHeap[j]) > 0)
				j += 1;
			
			if(theHeap[j].compareTo(item) > 0)
			{
				theHeap[k] = theHeap[j]; //push his max child up.
				k = j;
			}
			else break;
		}
		theHeap[k] = item;		
	}
	
	public void enqueue(T item)
	{
		theHeap[++size] = item;
		swimWithoutExch(size);
		if(size == capicity)
			resize(this.capicity * 2);
	}
	
	public T dequeue()
	{
		if(size == 0)
			throw new IllegalStateException();
		
		T ret = theHeap[1];
		theHeap[1] = theHeap[size--];
		sinkWithoutExch(1);
		
		if(size < this.capicity / 4)
			resize(this.capicity / 2);
		
		return ret;		
	}
	
	public T peek()
	{
		return size > 0 ? theHeap[1] : null;
	}
	
	public int size()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	private void resize(int newCap)
	{
		@SuppressWarnings("unchecked")
		T[] newHeap = (T[])new Comparable[newCap + 1];
		
		for(int i = 1 ; i <= size ; i++)
			newHeap[i] = theHeap[i];
		
		this.capicity = newCap;
		
		theHeap = newHeap;
	}
	
	private void exch(int i, int j)
	{
		T t = theHeap[i];
		theHeap[i] = theHeap[j];
		theHeap[j] = t;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MaxPQ<Integer> q = new MaxPQ<Integer>(2);
		q.enqueue(1);
		q.enqueue(3);
		q.enqueue(7);
		q.enqueue(9);
		q.enqueue(2);
		q.enqueue(4);
		
		while(!q.isEmpty())
		{
			System.out.println(q.dequeue());			
		}
	}

}

package elementary;

import java.util.Iterator;

public class ArrayQueue<T> implements Iterable<T>{
	private T[] arr;
	private int DEFAULT_SIZE = 8;
	private int size = 0;
	private int head = 0, tail = 0;
	
	@SuppressWarnings("unchecked")
	public ArrayQueue()
	{
		arr = (T[])new Object[DEFAULT_SIZE];		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayQueue(int capicity)
	{
		arr = (T[])new Object[capicity];		
	}
	
	public void enqueue(T t)
	{
		if(isFull())
			reallocate(2 * size);
		arr[tail] = t;
		tail++;
		if(tail == arr.length)
			tail = 0;
		size++;
	}
	
	private void reallocate(int newSize)
	{
		@SuppressWarnings("unchecked")
		T[] newArr = (T[])new Object[newSize];
		if(head < tail)
		{
			for(int i = head, j = 0 ; i < tail ; i++, j++)
			{
				newArr[j] = arr[i];				
			}
		}else
		{
			int j = 0;
			for(int i = head ; i < arr.length ; i++)
			{
				newArr[j++] = arr[i];
			}
			for(int i = 0 ; i < tail ; i++)
			{
				newArr[j++] = arr[i];
			}
		}
		arr = newArr;
		head = 0;
		tail = size;
	}
	
	public T dequeue()
	{
		T ret = arr[head++];
		if(head == arr.length)
			head = 0;
		size--;
		
		if(size > 0 && size <= arr.length / 4)
			reallocate( arr.length / 2);
		
		return ret;
	}
	
	private boolean isFull()
	{
		return size == arr.length;		
	}
	
	public boolean isEmpty()
	{
		return size == 0;
	}
	public int size()
	{
		return size;
	}
	
	public Iterator<T> iterator() {
		return new ArrayQueueIterator();
	}
	
	private class ArrayQueueIterator implements Iterator<T>
	{
		private int index;
		public ArrayQueueIterator()
		{
			index = head;			
		}

		public boolean hasNext() {
			return index != tail;
		}

		public T next() {
			T ret = arr[index];
			index = (index + 1) % (arr.length);
			return ret;
		}

		public void remove() {
						
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayQueue<Integer> aq = new ArrayQueue<Integer>(5);
		
		aq.enqueue(1);
		aq.enqueue(2);
		aq.enqueue(3);
		aq.enqueue(4);
		aq.enqueue(5);
		aq.dequeue();
		aq.dequeue();
		aq.dequeue();
		aq.enqueue(16);
		aq.enqueue(17);
		aq.enqueue(18);
		aq.enqueue(19);
		
		
		while(!aq.isEmpty())
		{
			System.out.println(aq.dequeue());
		}
		
		aq.enqueue(20);
		aq.enqueue(21);
		aq.enqueue(22);
		while(!aq.isEmpty())
		{
			System.out.println(aq.dequeue());
		}
		
		
	}

	

}

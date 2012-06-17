package elementary;

import java.util.Iterator;

public class Stack<T> implements Iterable<T>{
	
	private int size;
	private T[] a;
	private int capicity = 16;
	
	@SuppressWarnings("unchecked")
	public Stack()
	{
		a = (T[]) new Object[capicity];	
	}
	
	@SuppressWarnings("unchecked")
	public Stack(int capicity)
	{
		this.capicity = capicity;
		a = (T[]) new Object[capicity];
	}
	
	public void push(T item)
	{
		a[size++] = item;
		if(size == capicity)
			resize(2 * size);
	}
	
	private void resize(int newSize)
	{
		capicity = newSize;
		@SuppressWarnings("unchecked")
		T[] newArray = (T[]) new Object[capicity];
		for(int i = 0 ; i < size ; i++)
			newArray[i] = a[i];
		a = newArray;
	}
	
	public T pop()
	{
		T ret = a[--size];
		if(size < capicity / 4 )
			resize(capicity / 2);
		return ret;
	}
	
	public int size()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		return size == 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Iterator<T> iterator() {
		return new StackIterator(a);
	}
	
	private class StackIterator implements Iterator<T>
	{
		T[] a;
		int i;
		public StackIterator(T[] a)
		{
			this.a = a;
			i = size - 1;
		}

		public boolean hasNext() {
			return i >= 0;
		}

		public T next() {
			return a[i--];
		}

		public void remove() {
			;			
		}
	}

}

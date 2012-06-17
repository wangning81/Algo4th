package elementary;

import java.util.Iterator;

public class Bag <T> implements Iterable<T> {
	private LinkedList<T> list = new LinkedList<T>();
	
	public Bag()
	{
				
	}
	
	public void add(T item)
	{
		list.add(item);		
	}
	
	public void remove(T item)
	{
		list.remove(item);		
	}
	
	public int size()
	{
		return list.count();
	}

	public Iterator<T> iterator() {
		return list.iterator();
	}
	
	public boolean contains(T item)
	{
		return list.contains(item) != -1;		
	}
	
	public void clear()
	{
		list.clear();
	}
}

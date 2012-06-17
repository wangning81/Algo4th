package elementary;

public class CircularListQueue<T> {
	private class Node<E>
	{
		E Item;
		Node<E> Next;
	}
	
	private Node<T> last;
	private int size;
	
	public CircularListQueue()
	{
		
	}
	
	
	public void enqueue(T t)
	{
		Node<T> oldLast = last;
		last = new Node<T>();
		last.Item = t;
		if(oldLast != null)
		{
			last.Next = oldLast.Next;
			oldLast.Next = last;
		}
		else
		{
			last.Next = last;			
		}
		size++;
	}
	
	public T dequeue()
	{
		T ret = last.Next.Item;
		if(last.Next == last)
			last = null;
		else
			last.Next = last.Next.Next;
		size--;
		return ret;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

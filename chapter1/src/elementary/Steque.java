package elementary;

public class Steque<T> {
	
	private class Node<E>
	{
		E Item;
		Node<E> Next;
	}
	
	private Node<T> last;
	
	public void push(T t)
	{
		Node<T> newNode = new Node<T>();
		newNode.Item = t;
		if(last == null)
		{
			last = newNode;
			last.Next = last;
		}
		else
		{
			newNode.Next = last.Next;
			last.Next = newNode;
		}
		
	}
	
	public T pop()
	{
		T ret = last.Next.Item;
		if(last.Next == last)
		{
			last = null;
		}
		else
		{
			last.Next = last.Next.Next;
		}
		return ret;
	}
	
	public void enqueue(T t)
	{
		Node<T> oldLast = last;
		last = new Node<T>();
		last.Item = t;
		if(oldLast == null)
		{
			last.Next = last;
		}
		else
		{
			last.Next = oldLast.Next;
			oldLast.Next = last;
		}
	}
	
	public boolean isEmpty()
	{
		return last == null;
	}
	
	public void catenate(Steque<T> steque)
	{
		if(steque == null || steque.last == null)
			return;
		
		if(last == null)
		{
			last = steque.last;
			return;
		}
		
		Node<T> oldFirst = last.Next;
		last.Next = steque.last.Next;
		steque.last.Next = oldFirst;
		last = steque.last;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Steque<Integer> steq = new Steque<Integer>();
		steq.enqueue(1);
		steq.enqueue(2);
		steq.enqueue(3);
		steq.enqueue(4);
		steq.enqueue(5);
		
		Steque<Integer> steq2 = new Steque<Integer>();
		steq2.enqueue(11);
		steq2.enqueue(22);
		steq2.enqueue(33);
		steq2.enqueue(44);
		steq2.enqueue(55);
		
		steq.catenate(steq2);
		
		
		while(!steq.isEmpty())
		{
			System.out.println(steq.pop());
		}

	}

}

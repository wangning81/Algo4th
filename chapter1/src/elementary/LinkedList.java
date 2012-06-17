package elementary;
import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {

	public class Node
	{
		T Item;
		Node Next;
		public Node(T item, Node next)
		{
			Item = item;
			Next = next;
		}
	}
	
	private Node first;
	private int count;
	
	
	public LinkedList()
	{
		
	}
	
	public LinkedList(Iterable<T> list)
	{
		for(T t : list)
			add(t);
	}
	
	public void add(T item)
	{
		Node newNode = new Node(item, first);
		first = newNode;
		count++;
	}
	
	public void append(Node n)
	{
		if(first == null) first = n;
		else
		{
			Node c = first;
			while(c.Next != null)
				c = c.Next;
			c.Next = n;
		}
		count++;
	}
	
	public void add(Iterable<T> items)
	{
		if(items == null) return;
		for(T t : items)
			add(t);
	}
	
	public int count()
	{
		return count;
	}
	
	public int contains(T t)
	{
		Node it = first;
		int i = 1;
		while(it != null)
		{
			if(it.Item.equals(t))
				return i;
			it = it.Next;
			i++;
		}
		return -1;
	}
	
	public int remove(T t)
	{
		Node prev = null;
		Node cur = first;
		int cnt = 0;
		
		while(cur != null)
		{
			if(cur.Item.equals(t))
			{
				if(prev == null)
				{
					first = first.Next;
					cur = first;
				}
				else
				{
					prev.Next = cur.Next;
					cur = cur.Next;
				}
				cnt++;
				count--;
			}
			else
			{
				prev = cur;
				cur = cur.Next;
			}
		}
		return cnt;
	}
	
	public void clear()
	{
		this.first = null;
		this.count = 0;
	}
	
	public boolean delete(int index)
	{
		Node cur = first;
		Node prev = null;
		int i = 0;
		while(cur != null)
		{
			if(i == index)
			{
				if(prev == null)
					first = first.Next;
				else
					prev.Next = cur.Next;
				count--;
				return true;
			}
			else
			{
				prev = cur;
			}
			cur = cur.Next;
			i++;			
		}
		return false;
	}
	
	public boolean removeAfter(Node node)
	{
		if(node == null)
			return false;
		
		Node cur = first;
		while(cur != null)
		{
			if(cur == node)
			{
				cur.Next = cur.Next.Next;
				return true;
			}
			cur = cur.Next;
		}
		return false;
	}
	
	public boolean insertAfter(Node node, Node nodeToBeInserted)
	{
		if(node != null && nodeToBeInserted != null)
		{
			nodeToBeInserted.Next = node.Next;
			node.Next = nodeToBeInserted;
			return true;
		}
		return false;
	}
	
	public void reverse()
	{
		Node cur = first;
		Node prev = null;
		
		while(cur != null)
		{
			Node next = cur.Next;
			cur.Next = prev;
			prev = cur;
			cur = next;
		}
		
		first = prev;
		return;
	}
	
	public void reverseRecursive()
	{
		first = reserveRecursiveImp(first);
	}
	
	public Node reserveRecursiveImp(Node n)
	{
		if(n == null)
			return null;
		if(n.Next == null)
			return n;
		
		Node next = n.Next;
		Node newHead = reserveRecursiveImp(n.Next);
		
		next.Next = n;
		n.Next = null;
		
		return newHead;
	}
	
	@SuppressWarnings("unchecked")
	public Comparable<T> max()
	{
		if(first == null)
			return null;
		
		Comparable<T> ret = null;
		if (first.Item instanceof Comparable)
		{
			ret = (Comparable<T>)first.Item;
			Node cur = first;
			
			while(cur != null)
			{
				Comparable<T> curItem = (Comparable<T>)cur.Item;
				if(ret.compareTo((T)curItem) < 0)
				{
					ret = curItem;
				}
				cur = cur.Next;
			}
		}
		
		return ret;
	}
	
	public Comparable<T> maxRecusive()
	{
		return maxRecusiveImp(first);
	}
	
	@SuppressWarnings("unchecked")
	private Comparable<T> maxRecusiveImp(Node n)
	{
		if(n == null)
			return null;
		
		if (n.Item instanceof Comparable)
		{
			Comparable<T> subMax = maxRecusiveImp(n.Next);
			if(subMax == null || ((Comparable<T>)n.Item).compareTo((T)subMax) > 0)
				return (Comparable<T>)n.Item;
			else
				return subMax;
		}
		else
			return null;
		
	}
	
	public Iterator<T> iterator() {
		return new LinkedListIterator(first);
	}
	
	private class LinkedListIterator implements Iterator<T>
	{
		private Node cur;
		
		public LinkedListIterator(Node first)
		{
			cur = first;			
		}
		public boolean hasNext() {
			return cur != null;
		}

		public T next() {
			T item = cur.Item;
			cur = cur.Next;
			return item;
		}

		public void remove() {
						
		}
	}
	
	public boolean hasCircle()
	{
		Node tortoise, hare;
		tortoise = hare = first;
		
		while(tortoise != null && hare != null)
		{
			for(int i = 0 ; i < 2 ; i++)
			{
				hare = hare.Next;
				if(hare == null) return false;
				if(hare == tortoise) return true;				
			}
			tortoise = tortoise.Next;
		}
		return false;		
	}
	
	public int measureCircle(Node[] item)
	{
		Node tortoise = first;
		Node hare = first.Next.Next;
		
		while(tortoise != hare)
		{
			tortoise = tortoise.Next;
			hare = hare.Next.Next;
		}
		
		tortoise = first;
		
		while(tortoise != hare)
		{
			tortoise = tortoise.Next;
			hare = hare.Next;
		}
		
		item[0] = tortoise;
		
		int len = 1;
		hare = hare.Next;
		
		while(hare != tortoise)
			hare = hare.Next;
			
		return len;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinkedList<Integer> intList = new LinkedList<Integer>();
		intList.add(1);
		intList.add(2);
		intList.add(22);
		intList.add(12);
		intList.add(3);
		intList.add(4);
		
		System.out.println("Original array");
		intList.reverseRecursive();
		for(Integer i : intList)
		{
			System.out.print(i + ",");
		}
		
		System.out.println();
		
//		System.out.println("after remove");
//		intList.remove(2);		
//		for(Integer i : intList)
//		{
//			System.out.print(i + ",");
//		}
		
		System.out.println("\nmax");
		System.out.println(intList.maxRecusive());
		
		intList.clear();
		
		LinkedList<Integer>.Node first = intList.new Node(1, null);
		LinkedList<Integer>.Node sec = intList.new Node(2, null);
		LinkedList<Integer>.Node third = intList.new Node(3, null);
		
		first.Next = sec;
		sec.Next = third;
		third.Next = first;
		
		intList.append(first);
		
		System.out.println(intList.hasCircle());

	}


	

		


}

package priorityqueue;

public class DynamicMedianFinding<T extends Comparable<T>> {
	private MaxPQ<T> lessers = new MaxPQ<T>();
	private MinPQ<T> greaters = new MinPQ<T>();
	private int size = 0;
	
	public DynamicMedianFinding()
	{
				
	}
	
	public void insert(T item)
	{
		int cmp = size > 0 ? item.compareTo(median()) : -1;
		if(cmp <= 0) lessers.enqueue(item);						
		else greaters.enqueue(item);
		size++;
		balance();
	}

	private void balance() {
		if(greaters.size() > lessers.size())
			lessers.enqueue(greaters.dequeue());
		else if(lessers.size() > greaters.size() + 1)
			greaters.enqueue(lessers.dequeue());
	}
	
	public T median()
	{
		return lessers.size() > 0 ?  lessers.peek() : null;		
	}
	
	public T delMedian()
	{
		if(size > 0)
		{
			T ret = lessers.dequeue();
			size--;
			balance();
			return ret;
		}
		return null;
	}
	
	public static void main(String[] args) {
		DynamicMedianFinding<Integer> dmf = new DynamicMedianFinding<Integer>();
		dmf.insert(10);
		System.out.println(dmf.median());
		dmf.insert(20);
		System.out.println(dmf.median());
		dmf.insert(20);
		System.out.println(dmf.median());
		dmf.insert(1);
		System.out.println(dmf.median());
		dmf.insert(100);
		dmf.insert(100);
		dmf.insert(100);
		System.out.println(dmf.median());
		dmf.insert(100);
		System.out.println(dmf.median());
		dmf.insert(100);
		System.out.println(dmf.median());
		System.out.println(dmf.delMedian());
		System.out.println(dmf.delMedian());
		System.out.println(dmf.delMedian());
	}
}

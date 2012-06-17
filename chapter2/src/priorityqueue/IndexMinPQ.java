package priorityqueue;

public class IndexMinPQ<T extends Comparable<T>> {
	private T[] minHeap;
	private int[] indexToHeapPos;
	private int[] heapPosToIndex;
	private int size;
		
	@SuppressWarnings("unchecked")
	public IndexMinPQ(int maxN)
	{
		indexToHeapPos = new int[maxN];
		heapPosToIndex = new int[maxN + 1];
		minHeap = (T[]) new Comparable[maxN + 1];
		size = 0;
	}
	
	public void insert(int index, T item)
	{
		if(indexToHeapPos[index] != 0)
		{
			change(index, item);
			return;
		}
		
		minHeap[++size] = item;
		swim(size, index);
	}
	
	public void change(int index, T item)
	{
		int hp = indexToHeapPos[index]; 
		if(hp == 0)
		{
			insert(index, item);
			return;
		}
		
		minHeap[hp] = item;
		
		if(hp > 1 && minHeap[hp].compareTo(minHeap[hp/2]) < 0)
			swim(hp, index);
		else
			sink(hp, index);
	}
	
	public boolean contains(int k)
	{
		return indexToHeapPos[k] != 0;
	}
	
	public void delete(int index)
	{
		int hp = indexToHeapPos[index];
		indexToHeapPos[index] = 0;
		
		int succIndex = heapPosToIndex[size]; 
		minHeap[hp] = minHeap[size--];
		
		if(hp > 1 && minHeap[hp].compareTo(minHeap[hp/2]) < 0)
			swim(hp, succIndex);
		else
			sink(hp, succIndex);
	}
	
	public T min()
	{
		return minHeap[1];		
	}
	
	public int minIndex()
	{
		return heapPosToIndex[1];
	}
	
	public int delMin()
	{
		int index = heapPosToIndex[1];
		indexToHeapPos[index] = 0;
				
		int succIndex = heapPosToIndex[size]; 
		minHeap[1] = minHeap[size--];
		
		sink(1, succIndex);
		
		return index;
	}
	
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	public int size()
	{
		return size;
	}
	
	private void sink(int hp, int index)
	{
		T item = minHeap[hp];
		while(hp <= size / 2)
		{
			int j = hp * 2;
			
			if(j < size && minHeap[j].compareTo(minHeap[j + 1]) > 0)
				j += 1;
			
			if(minHeap[j].compareTo(item) < 0)
			{
				minHeap[hp] = minHeap[j];
				indexToHeapPos[heapPosToIndex[j]] = hp;
				heapPosToIndex[hp] = heapPosToIndex[j];
				hp = j;
			}
			else
				break;
		}
		minHeap[hp] = item;
		indexToHeapPos[index] = hp;
		heapPosToIndex[hp] = index;
		
	}
	
	private void swim(int hp, int index)
	{
		T item = minHeap[hp];
		while(hp > 1 && item.compareTo(minHeap[hp/2]) < 0)
		{
			minHeap[hp] = minHeap[hp/2];
			indexToHeapPos[heapPosToIndex[hp/2]] = hp;
			heapPosToIndex[hp] = heapPosToIndex[hp/2];
			hp /= 2;
		}
		minHeap[hp] = item;
		indexToHeapPos[index] = hp;
		heapPosToIndex[hp] = index;
	}
	
	
	
	public static void main(String[] args) {
		IndexMinPQ<Integer> impq = new IndexMinPQ<Integer>(16);
		impq.insert(0, 3);
		impq.insert(2, 1);
		impq.insert(4, 2);
		
		System.out.println(impq.min());
		System.out.println(impq.minIndex());
		
		impq.delete(2);
		
		System.out.println(impq.min());
		System.out.println(impq.minIndex());
		
		System.out.println(impq.delMin());
		System.out.println(impq.minIndex());
		
		impq.insert(10, 2);
		
		System.out.println(impq.min());
		System.out.println(impq.minIndex());
		
		impq.change(10, 100);
		
		System.out.println(impq.min());
		System.out.println(impq.minIndex());
		
		System.out.println(impq.delMin());
		System.out.println(impq.min());
		System.out.println(impq.minIndex());

	}

}

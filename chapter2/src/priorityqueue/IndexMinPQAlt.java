package priorityqueue;

public class IndexMinPQAlt<T extends Comparable<T>> {
	private T[] minHeap;
	private int[] indexToHeapPos;
	private int[] heapPosToIndex;
	private int size;
		
	@SuppressWarnings("unchecked")
	public IndexMinPQAlt(int maxN)
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
		indexToHeapPos[index] = size;
		heapPosToIndex[size] = index;
		swim(size);
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
		
		swim(hp);
		sink(hp);
	}
	
	public boolean contains(int k)
	{
		return indexToHeapPos[k] != 0;
	}
	
	public void delete(int index)
	{
		int hp = indexToHeapPos[index];
		indexToHeapPos[index] = 0;
		heapPosToIndex[hp] = heapPosToIndex[size];
		minHeap[hp] = minHeap[size--];
		
		swim(hp);
		sink(hp);
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
		minHeap[1] = minHeap[size];
		heapPosToIndex[1] = heapPosToIndex[size];
		minHeap[size--] = null;
		
		sink(1);
		
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
	private void swim(int k)
	{
		while(k > 1 && minHeap[k].compareTo(minHeap[k/2]) < 0)
		{
			exch(k, k/2);
			k /= 2;
		}
	}
	
	private void sink(int k)
	{
		while(k <= size / 2)
		{
			int j = k * 2;
			
			if(j < size && minHeap[j].compareTo(minHeap[j + 1]) > 0)
				j += 1;
			
			if(minHeap[j].compareTo(minHeap[k]) < 0)
			{
				exch(k, j);
				k = j;
			}
			else
				break;
		}
	}
	
	private void exch(int j, int k)
	{
		T t = minHeap[j];
		minHeap[j] = minHeap[k];
		minHeap[k] = t;
		
		int tIndex = indexToHeapPos[heapPosToIndex[j]];
		indexToHeapPos[heapPosToIndex[j]] = indexToHeapPos[heapPosToIndex[k]];
		indexToHeapPos[heapPosToIndex[k]] = tIndex;
		
		int tHeapPos = heapPosToIndex[j];
		heapPosToIndex[j] = heapPosToIndex[k];
		heapPosToIndex[k] = tHeapPos;
	}
	
	public static void main(String[] args) {
		IndexMinPQ<Integer> impq = new IndexMinPQ<Integer>(16);
		impq.insert(0, 26);
		impq.insert(1, 38);
		impq.insert(2, 58);
		impq.insert(3, 19);
		impq.delMin();
	}

}

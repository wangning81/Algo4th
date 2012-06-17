package unionfind;

public class WeightedQuickFind extends QuickFind{

	private int[] size; 
	public WeightedQuickFind(int n)
	{
		super(n);
		
		size = new int[n];
		for(int i = 0 ; i < n ; i++)
			size[i] = 1;
	}
	
	@Override
	public void union(int p, int q)
	{
		int pRoot = find(p);
		int qRoot = find(q);
		
		if(pRoot == qRoot) return;
		
		int newRoot;
		int oldRoot;
		if(size[pRoot] > size[qRoot])
		{
			newRoot = pRoot;
			oldRoot = qRoot;
		}
		else
		{
			newRoot = qRoot;
			oldRoot = pRoot;
		}
		int newSize = size[pRoot] + size[qRoot];
		
		for(int i = 0, j = 0 ; i < id.length && j < newSize; i++)
			if(id[i] == oldRoot || id[i] == newRoot)
			{
				if(id[i] == oldRoot)
					id[i] = newRoot;
				size[i] = newSize;
				j++;
			}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WeightedQuickFind network = new WeightedQuickFind(10);
		network.union(0, 1);
		network.union(0, 2);
		network.union(0, 3);
		network.union(2, 4);
		
		System.out.println(network.connected(1, 4));

	}

}

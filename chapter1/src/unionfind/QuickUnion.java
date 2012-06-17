package unionfind;

public class QuickUnion extends UnionFind{
	protected int[] id;
	
	public QuickUnion(int n)
	{
		id = new int[n];
		
		for(int i = 0 ; i < n ; i++)
			id[i] = i;
	}
	
	@Override
	public int find(int p)
	{
		while(p != id[p])
			p = id[p];
		return p;
	}
	
	@Override
	public void union(int p, int q)
	{
		int pRoot = find(p);
		int qRoot = find(q);
		
		if(pRoot == qRoot) return;
		
		id[pRoot] = qRoot;
		return;		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QuickUnion network = new QuickUnion(10);
		network.union(0, 1);
		network.union(0, 2);
		network.union(0, 3);
		network.union(2, 4);
		
		System.out.println(network.connected(1, 3));
		//System.out.println(network.connected(1,4));
		
	}

}

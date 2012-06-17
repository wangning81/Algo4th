package unionfind;

public class PathCompressedWeightedQuickUnion extends WeightedQuickUnion {

	public PathCompressedWeightedQuickUnion(int n)
	{
		super(n);
	}
	
	protected void compressPath(int p, int root)
	{
		while(id[p] != root)
		{
			int r = p;
			p = id[p];
			id[r] = root;
		}
	}
	
	@Override
	public void union(int p, int q)
	{
		int pRoot = find(p);
		int qRoot = find(q);
		
		if(pRoot == qRoot) return;
		
		int newRoot;
		if(sz[pRoot] > sz[qRoot]) 
		{
			id[qRoot] = newRoot = pRoot;
			sz[pRoot] += sz[qRoot];
		}
		else
		{
			id[pRoot] = newRoot = qRoot;
			sz[qRoot] += sz[pRoot];
		}
		
		compressPath(p, newRoot);
		compressPath(q, newRoot);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PathCompressedWeightedQuickUnion network = new PathCompressedWeightedQuickUnion(10);
		network.union(0, 1);
		network.union(0, 2);
		network.union(0, 3);
		network.union(2, 4);
		network.union(5, 6);
		
		System.out.println(network.connected(1, 6));
	}

}

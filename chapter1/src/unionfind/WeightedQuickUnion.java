package unionfind;

public class WeightedQuickUnion extends QuickUnion {
	
	protected int[] sz;
	
	public WeightedQuickUnion(int n) 
	{
		super(n);
		sz = new int[n];
		
		for(int i = 0 ; i < n ; i++)
			sz[i] = 1;
	}
	
	@Override
	public void union(int p, int q)
	{
		int pRoot = find(p);
		int qRoot = find(q);
		
		if(pRoot == qRoot) return;
		
		if(sz[pRoot] > sz[qRoot]) 
		{
			id[qRoot] = pRoot;
			sz[pRoot] += sz[qRoot];
		}
		else
		{
			id[pRoot] = qRoot;
			sz[qRoot] += sz[pRoot];
		}
	}
	
	public int size(int p)
	{
		int pRoot = find(p);
		return sz[pRoot];
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}

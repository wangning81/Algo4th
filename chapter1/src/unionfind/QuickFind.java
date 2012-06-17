package unionfind;

public class QuickFind extends UnionFind {
	
	protected int[] id;
	
	public QuickFind(int n)
	{
		id = new int[n];
		for(int i = 0 ; i < n ; i++)
			id[i] = i;
	}
	
	@Override
	public int find(int p)
	{
		return id[p];
	}
	
	@Override
	public void union(int p, int q)
	{
		int pRoot = find(p);
		int qRoot = find(q);
		
		if(pRoot == qRoot) return;
		
		for(int i = 0 ; i < id.length ; i++)
			if(id[i] == pRoot)
				id[i] = qRoot;		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

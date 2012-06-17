package unionfind;

public class PathCompressedQuickUnion extends QuickUnion {
	public PathCompressedQuickUnion(int n)
	{
		super(n);		
	}
	
	@Override
	public void union(int p, int q)
	{
		int pRoot = find(p);
		int qRoot = find(q);
		
		if(pRoot == qRoot) return;
		
		id[pRoot] = qRoot;
		
		int s;
		while(id[p] != qRoot || id[q] != qRoot)
		{
			if(id[p] != qRoot)
			{
				s = id[p];
				id[p] = qRoot;
				p = s; 
			}
			if( id[q] != qRoot )
			{
				s = id[q];
				id[q] = qRoot;
				q = s;
			}
		}
	}
	
	public static void main(String[] args) {
		PathCompressedQuickUnion network = new PathCompressedQuickUnion(5);
		network.union(0, 1);
		network.union(1, 2);
		network.union(2, 3);
		network.union(3, 4);
		//network.union(5, 6);
		
		//System.out.println(network.connected(1, 6));
	}

}

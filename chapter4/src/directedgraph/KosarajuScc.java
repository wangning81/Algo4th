package directedgraph;

public class KosarajuScc {
	private Digraph g;
	private int[] sccId;
	private int sccCount = 1;
	private boolean[] marked;
	
	public KosarajuScc(Digraph g)
	{
		this.g = g;
		this.sccId = new int[g.verticeCount()];
		this.marked = new boolean[g.verticeCount()];
		
		DfsOrder dfsOrder = new DfsOrder(g.transpose());
		Iterable<Integer> tgRevPostOrder = dfsOrder.reversePostOrder();
		for(int v : tgRevPostOrder)
			if(!marked[v])
			{
				dfs(v);
				sccCount++;
			}
				
	}
	
	private void dfs(int s)
	{
		marked[s] = true;
		sccId[s] = sccCount;
		for(int v : g.adj(s))
			if(!marked[v])
				dfs(v);
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

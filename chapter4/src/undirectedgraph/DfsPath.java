package undirectedgraph;

import elementary.Stack;

public class DfsPath {
	private boolean[] marked;
	private int s;
	private int[] edgeTo;
	private Graph g;
	
	public DfsPath(Graph g, int s)
	{
		this.g = g;
		marked = new boolean[g.verticeCount()];
		edgeTo = new int[g.verticeCount()];
		this.s = s;
		dfs(s);
	}
	
	public boolean hasPathTo(int v)
	{
		return marked[v];
	}
	
	public Iterable<Integer> pathTo(int v)
	{
		if(hasPathTo(v))
			return null;
		Stack<Integer> st = new Stack<Integer>();
		for(int x = v ; x != s ; x = edgeTo[x])
			st.push(x);
		st.push(s);
		return st;		
	}
	
	private void dfs(int v)
	{
		marked[v] = true;
		for(int w : g.adj(v))
			if(!marked[w])
			{
				edgeTo[w] = v;
				dfs(w);
			}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

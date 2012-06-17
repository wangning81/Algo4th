package directedgraph;

import elementary.*;

public class DirectedDfsPath {
	private Digraph g;
	int s;
	private boolean[] marked;
	private int[] edgeTo;
	
	public DirectedDfsPath(Digraph g, int s)
	{
		this.g = g;
		this.marked = new boolean[g.verticeCount()];
		this.edgeTo = new int[g.verticeCount()];
		this.s = s;
		dfs(s);
	}
	
	private void dfs(int s)
	{
		marked[s] = true;
		for(int v : g.adj(s))
		{
			if(!marked[v])
			{
				edgeTo[v] = s;
				dfs(s);
			}
		}
	}
	
	public Iterable<Integer> pathTo(int v)
	{
		if(!marked[v]) return null;
		
		Stack<Integer> st = new Stack<Integer>();
		for(int x = edgeTo[v] ; x != s ; x = edgeTo[x])
		{
			st.push(s);
		}
		st.push(s);
		return st;
	}
	
	
}

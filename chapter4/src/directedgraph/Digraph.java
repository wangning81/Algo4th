package directedgraph;

import undirectedgraph.Graph;

public class Digraph extends Graph{
	public Digraph(int v)
	{
		super(v);
	}
	
	@Override
	public void addEdge(int u, int v)
	{
		this.adj[u].add(v);
		this.E++;
	}
	
	public Digraph transpose()
	{
		Digraph dg = new Digraph(this.V);
		for(int v = 0 ; v < V ; v++)
			for(int u : adj[v])
				dg.addEdge(u, v);
		return dg;
	}
}

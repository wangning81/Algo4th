package undirectedgraph;

import elementary.Bag;

public class EdgeWeightGraph {
	private Bag<Edge>[]	adj;
	private int V;
	private int E;
	
	@SuppressWarnings("unchecked")
	public EdgeWeightGraph(int v)
	{
		adj = new Bag[v];
		for(int i = 0 ; i < v ; i++)
		{
			adj[i] = new Bag<Edge>();			
		}
		
		V = v;
	}
	
	public void addEdge(Edge e)
	{
		int u = e.either() ; int v = e.other(u);
		adj[u].add(e);
		adj[v].add(e);
		E++;
	}
	
	public Iterable<Edge> adj(int v)
	{
		return adj[v];
	}
	
	public Iterable<Edge> edges()
	{
		Bag<Edge> ret = new Bag<Edge>();
		for(int v = 0 ; v < V ; v++)
		{
			for(Edge e : adj[v])
			{
				if(v < e.other(v)) //this is to prevent the duplication.
					ret.add(e);
			}
		}
		return ret;
	}
	
	public int verticesCount()
	{
		return V;
	}
	
	public int edgeCount()
	{
		return E;
	}
	
}

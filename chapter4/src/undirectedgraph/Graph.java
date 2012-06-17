package undirectedgraph;

import elementary.Bag;

public class Graph {

	protected Bag<Integer>[] adj;
	protected int E;
	protected int V;
	
	@SuppressWarnings("unchecked")
	public Graph(int v)
	{
		V = v;
		E = 0;
		adj = (Bag<Integer>[]) new Bag[v];
		for(int i = 0 ; i < V ; i++)
			adj[i] = new Bag<Integer>();
	}
	
	@SuppressWarnings("unchecked")
	public Graph(Graph g)
	{
		V = g.V;
		E = g.E;
		adj = (Bag<Integer>[]) new Bag[V]; 
		for(int i = 0 ; i < V ; i++)
			for(Integer e : g.adj[i])
				adj[i].add(e);
	}
	
	public void addEdgeWithConstraint(int u, int v)
	{
		if(adj[u].contains(v) || u == v) return;
		addEdge(u, v);
	}
	
	public void addEdge(int u, int v)
	{
		adj[u].add(v);
		adj[v].add(u);
		E++;		
	}
	
	public int edges(){return E;}
	public int verticeCount() {return V;}
	public Iterable<Integer> adj(int v){return adj[v];}
	public boolean hasEdge(int u, int v){return adj[u].contains(v);}
}

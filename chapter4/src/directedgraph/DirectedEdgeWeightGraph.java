package directedgraph;

import elementary.Bag;
import undirectedgraph.DirectedEdge;

public class DirectedEdgeWeightGraph{
	private Bag<DirectedEdge>[] adj;
	private int E;
	private int V;
	
	@SuppressWarnings("unchecked")
	public DirectedEdgeWeightGraph(int v) {
		V = v;
		adj = new Bag[v];
		for(int i = 0 ; i < v; i++)
			adj[i] = new Bag<DirectedEdge>();
	}
	
	public void addEdge(DirectedEdge e)
	{
		adj[e.from()].add(e);
		E++;
	}
	
	public Iterable<DirectedEdge> edges()
	{
		Bag<DirectedEdge> ret = new Bag<DirectedEdge>();
		for(int i = 0 ; i < adj.length ; i++)
			for(DirectedEdge e : adj[i])
				ret.add(e);
		return ret;
	}
	public Iterable<DirectedEdge> adj(int v)
	{
		Bag<DirectedEdge> ret = new Bag<DirectedEdge>();
		for(DirectedEdge e : adj[v])
			ret.add(e);
		return ret;
	}
	
	public int verticesCount()
	{
		return V;
	}
	public int edgesCount()
	{
		return E;
	}
	
	public Digraph ToDiGraph()
	{
		Digraph g = new Digraph(V);
		for(int i = 0 ; i < adj.length ; i++)
			for(DirectedEdge e : adj[i])
				g.addEdge(e.from(), e.to());
		return g;
	}
	
	public DirectedEdge getEdge(int u, int v)
	{
		for(DirectedEdge e : adj[u])
			if(e.to() == v)
				return e;
		return null;
	}
}

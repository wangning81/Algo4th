package shortestpath;

import undirectedgraph.DirectedEdge;
import directedgraph.DirectedEdgeWeightGraph;
import elementary.Stack;

public abstract class AbstractSP {
	protected double[] distTo;
	protected int[] vertexTo;
	protected DirectedEdge[] edgeTo;
	protected DirectedEdgeWeightGraph dewg;
	protected int s;
	protected int V;
	
	public AbstractSP(DirectedEdgeWeightGraph dewg, int s)
	{
		this.dewg = dewg;
		this.s = s;
		V = dewg.verticesCount();
		distTo = new double[dewg.verticesCount()];
		vertexTo = new int[dewg.verticesCount()];
		edgeTo = new DirectedEdge[dewg.verticesCount()];
		
		for(int i = 0 ; i < V ; i++)
		{
			distTo[i] = Double.MAX_VALUE;
		}
		
		distTo[s] = 0.0;
	}
	
	protected void relax(int v)
	{
		for(DirectedEdge e : dewg.adj(v))
		{
			int u = e.to();
			double newDist = distTo[v] + e.weight();
			if(newDist < distTo[u])
			{
				distTo[u] = newDist;
				vertexTo[u] = v;
				edgeTo[u] = e;
			}
		}
	}
	
	public boolean hasPathTo(int v)
	{
		return distTo[v] != Double.MAX_VALUE;
	}
	
	public double distTo(int v)
	{
		return distTo[v];
	}
	
	public Iterable<Integer> pathTo(int v)
	{
		if(!hasPathTo(v)) return null;
		Stack<Integer> st = new Stack<Integer>(); 
		for(int x = v ; x != s ; x = vertexTo[x])
		{
			st.push(x);
		}
		st.push(s);
		return st;		
	}
	
	public Iterable<DirectedEdge> edgePathTo(int v)
	{
		if(!hasPathTo(v)) return null;
		Stack<DirectedEdge> st = new Stack<DirectedEdge>();
		for(DirectedEdge e = edgeTo[v]; e != null ; e = edgeTo[e.from()])
		{
			st.push(e);
		}		
		return st;
	}
}

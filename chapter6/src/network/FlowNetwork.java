package network;

import elementary.Bag;

public class FlowNetwork {
	private Bag<FlowEdge>[] adjBag;
	private int V;
	
	@SuppressWarnings("unchecked")
	public FlowNetwork(int V)
	{
		this.V = V;
		adjBag = (Bag<FlowEdge>[])new Bag[V];
		for(int i = 0 ; i < V ; i++)
			adjBag[i] = new Bag<FlowEdge>();		
	}
	
	
	public void addEdge(FlowEdge edge)
	{
		adjBag[edge.from()].add(edge);
		adjBag[edge.to()].add(edge);
	}
	
	public void addEdge(int from, int to, double capacity)
	{
		addEdge(new FlowEdge(from, to, capacity));
	}
	
	public Bag<FlowEdge> adj(int v)
	{
		return adjBag[v];
	}


	public int V() {
		return V;
	}
}

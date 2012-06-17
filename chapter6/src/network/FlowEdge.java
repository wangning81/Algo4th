package network;

class FlowEdge {
	private final double capacity;
	private final int from;
	private final int to;
	private double flow;
	
	public FlowEdge(int from, int to, double capacity)
	{
		this.capacity = capacity;
		this.from = from;
		this.to = to;
	}
	
	public int from()
	{
		return from;
	}
	
	public int to()
	{
		return to;
	}
	
	public double capacity()
	{
		return capacity;
	}
	
	public void addFlowTo(int u, double delta)
	{
		if(u == from) flow -= delta;
		else if(u == to) flow += delta;
		else throw new IllegalArgumentException("Inconsistent edge '" + u + "' in adding flow ");		
	}
	
	public double residualCapicityTo(int v)
	{
		if(v == to) return this.capacity - flow;
		if(v == from) return flow;
		throw new IllegalArgumentException("Inconsistent edge '" + v + "' in adding flow ");
	}

	public int other(int u) {
		if(u == from) return to;
		else if(u == to) return from;
		throw new IllegalArgumentException("Inconsistent edge '" + u + "' in adding flow ");
	}
}

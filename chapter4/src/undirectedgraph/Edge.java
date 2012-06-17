package undirectedgraph;

public class Edge implements Comparable<Edge> {
	private int u;
	private int v;
	private double weight;
	
	public Edge(int u, int v, double weight)
	{
		this.u = u;
		this.v = v;
		this.weight = weight;
	}
	
	public double weight()
	{
		return weight;
	}
	
	public int either()
	{
		return u;
	}
	
	public int other(int w)
	{
		if(w == u) return v;
		if(w == v) return u;
		throw new IllegalArgumentException(); 
	}

	public int compareTo(Edge rhs) {
		if(this.weight > rhs.weight) return 1;
		if(this.weight < rhs.weight) return -1;
		return 0;
	}

}

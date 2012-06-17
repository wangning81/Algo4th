package undirectedgraph;

public class DirectedEdge  {
	private double weight;
	private int from;
	private int to;
		
	public DirectedEdge(int from, int to, double weight)
	{
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	public int from()
	{
		return from;
	}
	
	public int to()
	{
		return to;
	}
	
	public double weight()
	{
		return weight;
	}
}

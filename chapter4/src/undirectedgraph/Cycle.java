package undirectedgraph;

public class Cycle {
	
	private Graph g;
	private boolean[] marked;
	private boolean hasCycle;
	
	public Cycle(Graph g, int s)
	{
		this.g = g;
		marked = new boolean[g.verticeCount()];
		gfs(s, -1);
	}
	
	
	private void gfs(int v, int from)
	{
		marked[v] = true;
		boolean fromEdgeSeen = false;
		for(int u : g.adj(v))
		{
			if(u == from && !fromEdgeSeen)
				fromEdgeSeen = true;
			else if (!marked[u])
				gfs(u, v);
			//visiting marked vertex and if it is the from vertex,
			//it is not the first time we've seen it, so it must fall in
			//1 of these 3 cases:
			// 		a. a simple cycle.
			//		b. a parallel edge.
			//		c. a self loop.
			//		either way, it is a cycle.
			else 
				hasCycle = true;
		}
	}
	
	public boolean hasCycle()
	{
		return hasCycle;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph g = new Graph(4);
		g.addEdge(0, 1);
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		//g.addEdgeWithoutConstraint(2, 0);
		
		Cycle cycle = new Cycle(g, 0);
		System.out.println(cycle.hasCycle);
	}

}

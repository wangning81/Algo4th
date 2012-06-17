package undirectedgraph;

public class ParallelEdgeDetector {
	private Graph g;
	private boolean[] marked;
	private int parallelCount;
	
	public ParallelEdgeDetector(Graph g)
	{
		this.g = g;
		marked = new boolean[g.verticeCount()];
		for(int i = 0 ; i < g.verticeCount() ; i++)
			if(!marked[i])
				dfsToFindParallel(i, -1);
	}
	
	private void dfsToFindParallel(int s, int from)
	{
		marked[s] = true;
		boolean hasSeenSource = false;
		for(int v : g.adj(s))
		{
			if(v == from)
			{
				if(hasSeenSource) parallelCount++;
				else hasSeenSource = true;
			}
			else if(!marked[v])
				dfsToFindParallel(v, s);
		}
	}
	
	public int parallelCount()
	{
		return parallelCount;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph g = new Graph(4);
		g.addEdge(0, 1);
		g.addEdge(1, 0);
		g.addEdge(1, 0);
		g.addEdge(1, 0);
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		//g.addEdgeWithoutConstraint(3, 2);
		
		ParallelEdgeDetector ped = new ParallelEdgeDetector(g);
		
		System.out.println(ped.parallelCount());

	}

}

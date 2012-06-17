package graphHelper;

import undirectedgraph.Graph;

public class RemovableVertexFinder {
	
	public static int getRemovableVertex(Graph g)
	{
		boolean[] marked = new boolean[g.verticeCount()];
		return getRemovableVertex(g, 0, marked);		
	}
	
	public static int getRemovableVertex(Graph g, int s, boolean[] marked)
	{
		marked[s] = true;
		for(int v : g.adj(s))
			if(!marked[v])
				return getRemovableVertex(g, v, marked);
		return s;
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph g = GraphBuilder.FromFile("tinyG.txt");
		System.out.println(getRemovableVertex(g));
	}

}

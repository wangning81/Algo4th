package undirectedgraph;

import graphHelper.GraphBuilder;
import unionfind.PathCompressedWeightedQuickUnion;

public class UnionFindGraphSearch {
	
	private PathCompressedWeightedQuickUnion unionFind;
	
	public UnionFindGraphSearch(Graph g)
	{
		unionFind = new PathCompressedWeightedQuickUnion(g.verticeCount());
		for(int i = 0 ; i < g.verticeCount() ; i++)
			for(int e : g.adj(i))
				unionFind.union(i, e);
	}
	
	public boolean isConnected(int u, int v)
	{
		return unionFind.connected(u, v);
	}
	
	public int connectedCount(int v)
	{
		//do not count v itself
		return unionFind.size(v) - 1;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph g = GraphBuilder.FromFile("tinyG.txt");
		UnionFindGraphSearch ufgs = new UnionFindGraphSearch(g);
		System.out.println(ufgs.isConnected(0, 4));
		System.out.println(ufgs.isConnected(0, 12));
		System.out.println(ufgs.connectedCount(0));
		System.out.println(ufgs.connectedCount(12));

	}

}

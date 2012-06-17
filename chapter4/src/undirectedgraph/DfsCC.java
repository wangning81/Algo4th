package undirectedgraph;

import elementary.LinkedList;

public class DfsCC {

	private Graph g;
	private int[] cc;
	private boolean[] marked;
	private int count;
	
	public DfsCC(Graph g)
	{
		this.g = g;
		cc = new int[g.verticeCount()];
		marked = new boolean[g.verticeCount()];
		
		for(int i = 0 ; i < g.verticeCount() ; i++)
		{
			if(!marked[i])
			{
				dfs(i);
				count++;
			}
		}
	}
	
	private void dfs(int v)
	{
		marked[v]=true;
		cc[v] = count;
		for(int w : g.adj(v))
		{
			if(!marked[w])
			{
				dfs(w);
			}
		}
	}
	
	public int ccCount()
	{
		return count;
	}
	
	public int ccIndex(int v)
	{
		return cc[v];
	}
	
	public Iterable<Integer> verticesOfCC(int ccIndex)
	{
		LinkedList<Integer> ret = new LinkedList<Integer>(); 
		for(int i = 0 ; i < g.verticeCount(); i ++)
		{
			if(cc[i] == ccIndex)
				ret.add(i);
		}
		return ret;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

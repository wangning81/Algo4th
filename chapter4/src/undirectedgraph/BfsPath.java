package undirectedgraph;

import elementary.ArrayQueue;
import elementary.Stack;
import graphHelper.GraphBuilder;

public class BfsPath {
	
	private Graph g;
	private boolean[] marked;
	private int[] edgeTo;
	private int[] distTo;
	private int s;
	
	public BfsPath(Graph g, int s)
	{
		this.g = g;
		marked = new boolean[g.verticeCount()];
		edgeTo = new int[g.verticeCount()];
		distTo = new int[g.verticeCount()];
		bfs(s);
	}
	
	private void bfs(int s)
	{
		marked[s] = true;
		ArrayQueue<Integer> q = new ArrayQueue<Integer>();
		ArrayQueue<Integer> disQ = new ArrayQueue<Integer>();
		q.enqueue(s);
		disQ.enqueue(0);
				
		while(!q.isEmpty())
		{
			int u = q.dequeue();
			distTo[u] = disQ.dequeue();
			for(int v : g.adj(u))
			{
				if(!marked[v])
				{
					marked[v]=true;
					edgeTo[v] = u;
					q.enqueue(v);
					disQ.enqueue(distTo[u] + 1);
				}
			}
		}
	}
	
	public boolean hasPathTo(int v)
	{
		return marked[v];
	}
	
	public int distTo(int v)
	{
		return distTo[v];
	}
	
	public Iterable<Integer> pathTo(int v)
	{
		if(!hasPathTo(v))
			return null;
		
		Stack<Integer> st = new Stack<Integer>();
		for(int x = v ; x != s ; x = edgeTo[x])
			st.push(x);
		
		st.push(s);
		
		return st;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph g = GraphBuilder.FromFile("tinyG.txt");
		BfsPath bfs = new BfsPath(g, 10);
		for(int i = 0 ; i < g.verticeCount() ; i++)
			System.out.println(bfs.distTo(i));
	}

}

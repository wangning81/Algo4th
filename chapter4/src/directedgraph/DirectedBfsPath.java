package directedgraph;

import elementary.ArrayQueue;
import elementary.Stack;
import graphHelper.DigraphBuilder;

public class DirectedBfsPath {
	private Digraph g;
	private boolean[] marked;
	private int[] edgeTo;
	private int s;
	
	public DirectedBfsPath(Digraph g, int s)
	{
		this.g = g;
		this.marked = new boolean[g.verticeCount()];
		this.s = s;
		this.edgeTo = new int[g.verticeCount()];
		bfs(s);
	}
	
	private void bfs(int s)
	{
		ArrayQueue<Integer> q = new ArrayQueue<Integer>();
		q.enqueue(s);
		marked[s] = true;
		while(!q.isEmpty())
		{
			int v = q.dequeue();
			for(int u : g.adj(v))
				if(!marked[u])
				{
					marked[u] = true;
					q.enqueue(u);
					edgeTo[u] = v;
				}
		}
	}
	
	public Iterable<Integer> pathTo(int v)
	{
		if(!marked[v]) return null;
		
		Stack<Integer> st = new Stack<Integer>();
		for(int x = v ; x != s ; x = edgeTo[x])
		{
			st.push(x);	
		}
		st.push(s);
		
		return st;
	}
	
	public static void main(String[] args)
	{
		Digraph g = DigraphBuilder.FromFile("tinyG.txt");
		DirectedBfsPath dbp = new DirectedBfsPath(g, 0);
		for(int v : dbp.pathTo(5))
		{
			System.out.print(v + " ");
		}
	}

}

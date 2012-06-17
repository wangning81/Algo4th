package directedgraph;

import elementary.*;

public class DfsOrder {
	private Digraph g;
	private Stack<Integer> reversePostOrder = new Stack<Integer>();
	private ArrayQueue<Integer> preOrder = new ArrayQueue<Integer>();
	private ArrayQueue<Integer> postOrder = new ArrayQueue<Integer>();
	private boolean[] marked;
	
	public DfsOrder(Digraph g)
	{
		this.g = g;
		marked = new boolean[g.verticeCount()];
		for(int x = 0 ; x < g.verticeCount() ; x++)
		{
			if(!marked[x])
			{
				dfs(x);
			}
		}
	}
	
	public Iterable<Integer> reversePostOrder()
	{
		return reversePostOrder;		
	}
	
	public Iterable<Integer> preOrder()
	{
		return preOrder;
	}
	
	public Iterable<Integer> postOrder()
	{
		return postOrder;
	}
	
	private void dfs(int s)
	{
		preOrder.enqueue(s);
		marked[s] = true;
		for(int v : g.adj(s))
			if(!marked[v])
				dfs(v);
		postOrder.enqueue(s);
		reversePostOrder.push(s);
	}
}

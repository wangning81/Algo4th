package directedgraph;

public class DirectedDfs {
	private Digraph g;
	private boolean[] marked;
	public DirectedDfs(Digraph g, int s)
	{
		this.g = g;
		marked = new boolean[g.verticeCount()];
		dfs(s);
	}
	
	public DirectedDfs(Digraph g, Iterable<Integer> vertices)
	{
		this.g = g;
		marked = new boolean[g.verticeCount()];
		for(int v : vertices)
			dfs(v);
	}
	
	private void dfs(int v)
	{
		marked[v] = true;
		for(int w : g.adj(v))
		{
			if(!marked[w])
				dfs(w);
		}
	}
	
	public boolean isConnected(int v)
	{
		return marked[v];
	}
	
}

package undirectedgraph;

public abstract class GraphSearch {
	
	protected Graph g;
	protected int count;
	protected boolean[] marked;	
	
	public GraphSearch(Graph g, int s)
	{
		this.g = g;
		marked = new boolean[g.verticeCount()];
		search(s);
	}
	
	public int count() {
		return count;
	}
	
	public boolean mark(int v) {
		return marked[v];
	}
	
	protected abstract void search(int s);
	
}

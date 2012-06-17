package undirectedgraph;

public class SymbolDfsPath<T> extends SymbolPathFinder<T>{
	
	public SymbolDfsPath(SymbolGraph<T> g, T s)
	{
		super(g, s);		
	}
	
	protected void search(T u)
	{
		marked.put(u, true);
		for(T v : g.adj(u))
		{
			if(marked.get(v) == null || !marked.get(v))
			{
				edgeTo.put(v, u);
				search(v);
			}
		}
	}
}

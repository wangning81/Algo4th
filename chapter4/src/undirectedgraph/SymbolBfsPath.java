package undirectedgraph;

import elementary.ArrayQueue;


public class SymbolBfsPath<T> extends SymbolPathFinder<T> {
	public SymbolBfsPath(SymbolGraph<T> g, T s) {
		super(g, s);
	
	}

	protected void search(T v)
	{
		marked.put(v, true);
		ArrayQueue<T> q = new ArrayQueue<T>();
		q.enqueue(v);
		
		while(!q.isEmpty())
		{
			T u = q.dequeue();
			for(T w : g.adj(u))
			{
				if(marked.get(w) == null || !marked.get(w))
				{
					marked.put(w, true);
					edgeTo.put(w, u);
					q.enqueue(w);					
				}
			}
		}
	}
}

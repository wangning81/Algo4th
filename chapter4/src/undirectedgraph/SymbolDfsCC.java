package undirectedgraph;

import elementary.ArrayQueue;
import elementary.LinkedList;
import hashTables.LinearProbingHashST;
import symbolTables.SymbolTable;


public class SymbolDfsCC<T> {
	private SymbolGraph<T> g;
	private SymbolTable<T, Boolean> marked = new LinearProbingHashST<T, Boolean>();
	private int count;
	private SymbolTable<T, Integer> cc = new LinearProbingHashST<T, Integer>();
	private SymbolTable<Integer, Integer> ccSize = new LinearProbingHashST<Integer, Integer>();
	
	public SymbolDfsCC(SymbolGraph<T> g)
	{
		this.g = g;
		for(T v : g.vertices())
		{
			if(marked.get(v) == null || !marked.get(v))
			{
				count++;
				bfs(v);
			}
		}
	}
	
	private void bfs(T v)
	{
		ArrayQueue<T> q = new ArrayQueue<T>();
		
		marked.put(v, true);
		cc.put(v, count);		
		q.enqueue(v);
		
		int size = 0;
		
		while(!q.isEmpty())
		{
			T u = q.dequeue();
			cc.put(u, count);
			
			size++;
			
			for(T w : g.adj(u))
			{
				if(marked.get(w) == null || !marked.get(w))
				{
					marked.put(w, true);
					q.enqueue(w);
				}
			}
		}
		
		ccSize.put(count, size);
		
	}
	
	public int ccCount()
	{
		return count;
	}
	
	public int ccSize(int id)
	{
		return ccSize.get(id);
	}
	
	public Iterable<T> vertexInCC(int id)
	{
		LinkedList<T> ret = new LinkedList<T>();
		for(T v : cc.keys())
		{
			if(cc.get(v) == id)
				ret.add(v);
		}
		return ret;
	}
}

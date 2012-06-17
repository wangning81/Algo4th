package graphHelper;

import hashTables.SeparateChainingHashST;
import symbolTables.SymbolTable;
import undirectedgraph.SymbolGraph;
import elementary.ArrayQueue;

public class SymbolGraphProperties<T> {
	private SymbolTable<T, Integer> eccentricity = new SeparateChainingHashST<T, Integer>();
	private int diameter;
	private int radius;
	private T center;
	private int girth;
	private SymbolGraph<T> g;
	private Iterable<T> verticesToVisit;
		
	public SymbolGraphProperties(SymbolGraph<T> g)
	{
		this(g, g.vertices());
	}
	
	public SymbolGraphProperties(SymbolGraph<T> g, Iterable<T> verticesToVisit)
	{
		diameter = 0;
		radius = g.verticesCount() + 1;
		center = null;
		girth = Integer.MAX_VALUE;
		this.g = g;
		this.verticesToVisit = verticesToVisit;
		
		for(T s : this.verticesToVisit)
		{
			System.out.println(s);
			long now = System.currentTimeMillis();
			bfs(g, s);
			System.out.println(System.currentTimeMillis() - now);
		}
	}
	
	private void bfs(SymbolGraph<T> g, T s)
	{
		SymbolTable<T, Boolean> marked = new SeparateChainingHashST<T, Boolean>();
		ArrayQueue<T> q = new ArrayQueue<T>(); 
		ArrayQueue<Integer> disQ = new ArrayQueue<Integer>();
		
		marked.put(s, true);
		q.enqueue(s);
		disQ.enqueue(0);
		
		while(!q.isEmpty())
		{
			T u = q.dequeue();
			int dis = disQ.dequeue();
			Integer curEcc = eccentricity.get(s);
			if( curEcc == null || dis > curEcc)
				eccentricity.put(s, dis);
			
			for(T v : g.adj(u))
			{
				Boolean curMark = marked.get(v);
				if(curMark == null || !curMark)
				{
					marked.put(v, true);
					q.enqueue(v);	
					disQ.enqueue(dis + 1);
				}
			}
		}
		
		Integer finalEcc = eccentricity.get(s);
		
		if(finalEcc > diameter)
			diameter = finalEcc;
		
		if(finalEcc < radius)
		{
			radius = finalEcc;
			center = s;
		}
	}
	
	public int diameter()
	{
		return diameter;
	}
	
	public int eccentricity(T v)
	{
		return eccentricity.get(v);
	}
	
	public T center()
	{
		return center;
	}
	
	public int radius()
	{
		return radius;
	}
	
	public int girth()
	{
		for(T v : this.verticesToVisit)
		{
			int circleLen = bfsFindShortestCircle(v);
			if(circleLen < this.girth)
				this.girth = circleLen;
		}
		return this.girth;
	}

	private int bfsFindShortestCircle(T s) {
		ArrayQueue<T> q = new ArrayQueue<T>();
		SymbolTable<T, T> edgeTo = new SeparateChainingHashST<T, T>();
		SymbolTable<T, Boolean> marked = new SeparateChainingHashST<T, Boolean>();
		
		marked.put(s, true);
		edgeTo.put(s, s);
		
		q.enqueue(s);
		
		while(!q.isEmpty())
		{
			T u = q.dequeue();
			for(T v : g.adj(u))
			{
				Boolean curMark = marked.get(v);
				if(curMark == null || !curMark)
				{
					marked.put(v, true);
					edgeTo.put(v, u);
					q.enqueue(v);
				}
				//marked and it's not the source vertex, this is the shorted circle
				else if(v != edgeTo.get(u))
				{
					return calcCircleLen(u, v, edgeTo);									
				}
			}
		}
		
		return Integer.MAX_VALUE;
	}
	
	private int calcCircleLen(T u, T v, SymbolTable<T, T> edgeTo)
	{
		int ret = 1;
		while(u != v)
		{
			if(u != edgeTo.get(u))
			{
				u = edgeTo.get(u);
				ret++;
			}
			if(v != edgeTo.get(v))
			{
				v = edgeTo.get(v);
				ret++;
			}
		}
		return ret;
	}
}

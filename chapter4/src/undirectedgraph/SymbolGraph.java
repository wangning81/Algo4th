package undirectedgraph;

import elementary.Bag;
import elementary.LinkedList;
import symbolTables.SymbolTable;
import hashTables.SeparateChainingHashST;

public class SymbolGraph<T> {
	private SymbolTable<T, Bag<T>> adjList = new SeparateChainingHashST<T, Bag<T>>(); 
	private int edgeCount;
	
	public SymbolGraph()
	{
				
	}
	
	public Iterable<T> adj(T v)
	{
		Iterable<T> ret = adjList.get(v);
		if(ret == null) ret = new LinkedList<T>();
		return ret;
	}
	
	public int verticesCount()
	{
		return adjList.size();		
	}
	
	public Iterable<T> vertices()
	{
		return adjList.keys();
	}
	
	public int edgesCount()
	{
		return edgeCount;
	}
	
	public void addEdge(T u, T v)
	{
		if(!adjList.contains(u))
			adjList.put(u, new Bag<T>());
		if(!adjList.contains(v))
			adjList.put(v, new Bag<T>());
		adjList.get(u).add(v);
		adjList.get(v).add(u);
		edgeCount++;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SymbolGraph<String> g = new SymbolGraph<String>();
		g.addEdge("0", "1");
		g.addEdge("0", "2");
		g.addEdge("0", "6");
		g.addEdge("0", "5");
		g.addEdge("5", "3");
		g.addEdge("5", "4");
		g.addEdge("6", "4");
		g.addEdge("3", "4");
		g.addEdge("7", "8");
		g.addEdge("9", "10");
		g.addEdge("9", "11");
		g.addEdge("9", "12");
		g.addEdge("11", "12");
		
		for(String s : g.adj("0"))
			System.out.println(s);		

		SymbolDfsPath<String> p = new SymbolDfsPath<String>(g, "3");
		
		System.out.print(p.hasPathTo("11"));
		
		for(String s : p.pathTo("1"))
			System.out.println(s + " ");
		
		SymbolBfsPath<String> q = new SymbolBfsPath<String>(g, "3");
		
		for(String s : q.pathTo("1"))
			System.out.println(s + " ");
		
	}

}

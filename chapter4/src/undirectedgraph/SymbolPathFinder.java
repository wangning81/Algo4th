package undirectedgraph;

import elementary.Stack;
import hashTables.LinearProbingHashST;
import symbolTables.SymbolTable;

public abstract class SymbolPathFinder<T> {
	protected SymbolTable<T, Boolean> marked = new LinearProbingHashST<T, Boolean>();
	protected SymbolTable<T, T> edgeTo = new LinearProbingHashST<T, T>();
	protected T s;
	protected SymbolGraph<T> g;
	
	public SymbolPathFinder(SymbolGraph<T> g, T s)
	{
		this.g = g;
		this.s = s;
		
		for(T v : g.vertices()){
			marked.put(v, false);
			edgeTo.put(v, null);
		}
		
		search(s);
	}
	
	public boolean hasPathTo(T v)
	{
		Boolean ret = marked.get(v);
		return ret == null ? false : ret.booleanValue() ;
	}
	
	public Iterable<T> pathTo(T v)
	{
		if(!hasPathTo(v))
			return null;
		
		Stack<T> stack = new Stack<T>();
		for(T x = v ; x != s ; x = edgeTo.get(x))
			stack.push(x);
		stack.push(s);
		return stack;
	}
	
	protected abstract void search(T v);
	
}

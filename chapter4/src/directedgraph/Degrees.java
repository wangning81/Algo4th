package directedgraph;

import elementary.Bag;

public class Degrees {
	
	private Bag<Integer> sources = new Bag<Integer>();
	private Bag<Integer> sinks = new Bag<Integer>();
	private int[] indegree;
	private int[] outdegree;
	private boolean isMap = true;
	
	public Degrees(Digraph g)
	{
		for(int v = 0 ; v < g.verticeCount() ; v++)
		{
			int out = 0;
			for(int u : g.adj(v))
			{
				indegree[u]++;
				out++;
			}
			outdegree[v] = out;
		}
		
		for(int v = 0 ; v < g.verticeCount() ; v++)
		{
			if(indegree[v] == 0) sources.add(v);
			if(outdegree[v] == 0) sinks.add(v);
			if(outdegree[v] != 1) isMap = false; 
		}
	}
	
	public int indegree(int v)
	{
		return indegree[v];
	}
	
	public int outdegree(int v)
	{
		return outdegree[v];			
	}
	
	public Iterable<Integer> sources()
	{
		return sources;		
	}
	
	public Iterable<Integer> sinks()
	{
		return sinks;
	}
	
	public boolean isMap()
	{
		return isMap;
	}

}

package directedgraph;

import elementary.Stack;
import graphHelper.DigraphBuilder;

public class CycleDetector {
	
	private Digraph g;
	private boolean[] marked;
	private boolean[] onStack;
	private int[] edgeTo;
	private boolean hasCycle;
	private Stack<Integer> cycle;
	
	public CycleDetector(Digraph g)
	{
		this.g = g;
		marked = new boolean[g.verticeCount()];
		onStack = new boolean[g.verticeCount()];
		edgeTo = new int[g.verticeCount()];
				
		for(int v = 0 ; v < g.verticeCount() && !hasCycle ; v++)
			if(!marked[v])
				dfs(v);			
	}
	
	public boolean hasCycle()
	{
		return hasCycle;
	}
	
	public Iterable<Integer> cycle()
	{
		return cycle;
	}
	
	private void dfs(int s)
	{
		onStack[s] = true;
		marked[s] = true;
		for(int v : g.adj(s))
			if(hasCycle)
			{
				break;
			}
			else if(!marked[v])
			{
				edgeTo[v] = s;			
				dfs(v);
			}
			else if(onStack[v])
			{
				hasCycle = true;
				cycle = new Stack<Integer>();
				cycle.push(v);
				for(int x = s ; x != v ; x = edgeTo[x])
				{
					cycle.push(x);
				}
				cycle.push(v);
			}
		onStack[s] = false;
	}
	
	
	public static void main(String[] args) {
		Digraph g = DigraphBuilder.FromFile("tinyDG.txt");
		CycleDetector cd = new CycleDetector(g);
		System.out.println(cd.hasCycle());
		for(int c : cd.cycle())
		{
			System.out.print(c + " => ");
		}
		
	}

}

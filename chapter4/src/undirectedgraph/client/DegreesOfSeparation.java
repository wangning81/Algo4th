package undirectedgraph.client;

import graphHelper.GraphBuilder;
import hashTables.SeparateChainingHashST;
import elementary.Stack;
import symbolTables.SymbolTable;
import undirectedgraph.SymbolGraph;

public class DegreesOfSeparation {
	
	private SymbolGraph<String> g;
	
	public DegreesOfSeparation(SymbolGraph<String> g)
	{
		this.g = g;
	}
	
	public Iterable<String> relationBetweenDFS(String source, String dest)
	{
		return relationBetweenDFS(source, dest, new Stack<String>(), 
							new SeparateChainingHashST<String, Boolean>());	
	}
	
	private Iterable<String> relationBetweenDFS(String source, String dest, 
												Stack<String> s, 
												SymbolTable<String, Boolean> marked)
	{
		marked.put(source, true);
		s.push(source);
		
		while(!s.isEmpty())
		{
			String actOrMovie = s.pop();
			for(String adj : g.adj(actOrMovie))
			{
				if(adj.equals(dest))
				{
					s.push(actOrMovie);
					s.push(dest);
					return s;
				}
				else if(marked.get(adj) == null || !marked.get(adj))
				{
					marked.put(adj, true);					
					s.push(actOrMovie);
					s.push(adj);
					break;					
				}			
			}
		}
			
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SymbolGraph<String> sg = GraphBuilder.SymbolGraphFromFile("movies.txt", "/");
		
		DegreesOfSeparation dos = new DegreesOfSeparation(sg);
		for(String s : dos.relationBetweenDFS("Bacon, Kevin", "Kidman, Nicole"))
		{
			System.out.println(s);
		}
	}

}

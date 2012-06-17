package undirectedgraph.client;

import graphHelper.*;
import undirectedgraph.*;

public class SymbolGraphWithImdb {


	public static void main(String[] args) {
		SymbolGraph<String> sg = GraphBuilder.SymbolGraphFromFile("movies.txt", "/");
		findRelationshipWithKevinBacon(sg);
		//findCC(sg);
		//displayProperties(sg);
		
		
	}
	
	@SuppressWarnings("unused")
	private static void displayProperties(SymbolGraph<String> sg) {
		SymbolDfsCC<String> cc = new SymbolDfsCC<String>(sg);
		//System.out.println(((LinkedList)cc.vertexInCC(1)).count());
		SymbolGraphProperties<String> sgp = new SymbolGraphProperties<String>(sg, cc.vertexInCC(2)); 
		System.out.println(sgp.diameter());
		System.out.println(sgp.radius());
	}
	

	@SuppressWarnings("unused")
	private static void findCC(SymbolGraph<String> sg) {
		SymbolDfsCC<String> cc = new SymbolDfsCC<String>(sg);
		for(int i = 1 ; i <= cc.ccCount() ; i++)
		{
			System.out.println("Id = " + i + " Size = " + cc.ccSize(i));
		}
		
		for(String ss : cc.vertexInCC(31))
		{
			System.out.println(ss);
		}
	}

	private static void findRelationshipWithKevinBacon(SymbolGraph<String> sg) {
		
		SymbolBfsPath<String> sbp = new SymbolBfsPath<String>(sg, "Bacon, Kevin");
				
		for(String node : sbp.pathTo("Portman, Natalie"))
		{
			System.out.println(node);
		}
		System.out.println("===================================");
		for(String node : sbp.pathTo("Firth, Colin"))
		{
			System.out.println(node);
		}
		System.out.println("===================================");
		for(String node : sbp.pathTo("Leo, Melissa"))
		{
			System.out.println(node);
		}
		System.out.println("===================================");
		for(String node : sbp.pathTo("Bale, Christian"))
		{
			System.out.println(node);
		}
		System.out.println("===================================");
		
		for(String node : sbp.pathTo("Kidman, Nicole"))
		{
			System.out.println(node);
		}
		System.out.println("===================================");
		
		for(String node : sbp.pathTo("Zhang, Ziyi"))
		{
			System.out.println(node);
		}
		System.out.println("===================================");
		
		System.out.println("Finished");
	}

}

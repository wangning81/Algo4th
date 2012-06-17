package shortestpath;

import directedgraph.Digraph;
import directedgraph.TopologicalSort;
import directedgraph.DirectedEdgeWeightGraph;
import graphHelper.DirectedEdgeWeightGraphBuilder;

public class AcyclicSP extends AbstractSP {
	
	public AcyclicSP(DirectedEdgeWeightGraph dewg, int s) 
	{
		super(dewg, s);
		Digraph dg = dewg.ToDiGraph();
		Iterable<Integer> topologicalOrder = TopologicalSort.sort(dg);
		
		for(int v : topologicalOrder)
		{
			relax(v);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DirectedEdgeWeightGraph dewg = DirectedEdgeWeightGraphBuilder.FromFile("tinyEWDAG.txt", " ");
		AcyclicSP sp = new AcyclicSP(dewg, 6);
		for(Integer v : sp.pathTo(2))
		{
			System.out.print(v + " => ");			
		}
	}

}

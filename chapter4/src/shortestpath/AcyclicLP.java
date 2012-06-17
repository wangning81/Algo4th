package shortestpath;

import undirectedgraph.DirectedEdge;
import directedgraph.DirectedEdgeWeightGraph;
import directedgraph.TopologicalSort;

public class AcyclicLP {
	
	private double[] distTo;
	private int[] edgeTo;
		
	public AcyclicLP(DirectedEdgeWeightGraph dewg, int s)
	{
		Iterable<Integer> topOrder = TopologicalSort.sort(dewg.ToDiGraph());
		
		distTo = new double[dewg.verticesCount()];
		edgeTo = new int[dewg.verticesCount()];
		
		for(int i = 0 ; i < distTo.length ; i++)
			distTo[i] = Double.NEGATIVE_INFINITY;
					
		distTo[s] = 0.0;
				
		for(int v : topOrder)
		{
			relax(dewg, v);
		}
	}
	
	private void relax(DirectedEdgeWeightGraph dewg, int v)
	{
		for(DirectedEdge e : dewg.adj(v))
		{
			int w = e.to();
			if(distTo[v] + e.weight() > distTo[w]) //Longest path
			{
				distTo[w] = distTo[v] + e.weight();
				edgeTo[w] = v;
			}
		}	
	}
	
	public double distTo(int v)
	{
		return distTo[v];
	}

}

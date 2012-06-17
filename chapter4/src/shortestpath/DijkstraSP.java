package shortestpath;

import priorityqueue.IndexMinPQ;
import undirectedgraph.DirectedEdge;
import directedgraph.DirectedEdgeWeightGraph;
import graphHelper.DirectedEdgeWeightGraphBuilder;

public class DijkstraSP extends AbstractSP{
	
	private IndexMinPQ<Double> pq;
			
	public DijkstraSP(DirectedEdgeWeightGraph dewg, int s)
	{
		super(dewg, s);
		
		pq = new IndexMinPQ<Double>(V);
		
		pq.insert(s, 0.0);
		
		while(!pq.isEmpty())
		{
			relax(pq.delMin());
		}
	}
	
	@Override
	protected void relax(int v)
	{
		for(DirectedEdge e : dewg.adj(v))
		{
			int u = e.to();
			double newDist = v + e.weight();
			if(newDist < distTo[u])
			{
				vertexTo[u] = v;
				distTo[u] = newDist;
				edgeTo[u] = e;
				
				if(pq.contains(u)) pq.change(u, newDist);
				else pq.insert(u, newDist);
			}
		}
	}
	
	public static void main(String[] args)
	{
		DirectedEdgeWeightGraph dewg = DirectedEdgeWeightGraphBuilder.FromFile("tinyEWD.txt", " ");
		DijkstraSP sp = new DijkstraSP(dewg, 0);
		for(Integer v : sp.pathTo(7))
		{
			System.out.print(v + " => ");			
		}
		
		System.out.println("========================");
		
		for(DirectedEdge e : sp.edgePathTo(7))
		{
			System.out.print(e.from() + " => " + e.to() + ",");
		}
	}
}

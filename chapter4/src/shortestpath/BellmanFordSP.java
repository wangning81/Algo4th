package shortestpath;

import undirectedgraph.DirectedEdge;
import directedgraph.CycleDetector;
import directedgraph.DirectedEdgeWeightGraph;
import directedgraph.Digraph;
import elementary.ArrayQueue;
import graphHelper.DirectedEdgeWeightGraphBuilder;

public class BellmanFordSP extends AbstractSP{
	private ArrayQueue<Integer> vq = new ArrayQueue<Integer>();
	private boolean[] onVq;
	private int cost = 0;
	private Iterable<Integer> negativeCycle;
	
	public BellmanFordSP(DirectedEdgeWeightGraph dewg, int s)
	{
		super(dewg, s);
		
		onVq = new boolean[V];
		vq.enqueue(s);
		onVq[s] = true;
		while(!vq.isEmpty() && !hasNegativeCycle())
		{
			int v = vq.dequeue();
			onVq[v] = false;
			relax(v);
		}
	}
	
	@Override
	protected void relax(int v)
	{
		for(DirectedEdge e : dewg.adj(v))
		{
			int w = e.to();
			double newDist = distTo[v] + e.weight();
			if(newDist < distTo[w])
			{
				distTo[w] = newDist;
				vertexTo[w] = v;
				edgeTo[w] = e;
				if(!onVq[w])
				{
					vq.enqueue(w);
					onVq[w] = true;
				}
			}
			
			if(cost++ % dewg.verticesCount() == 0)
			{
				findNegativeCycle();
			}
		}
	}
	
	public boolean hasNegativeCycle()
	{
		return negativeCycle != null;
	}
	
	public Iterable<Integer> negativeCycle()
	{
		return negativeCycle;
	}
	
	private void findNegativeCycle()
	{
		//1. construct a digraph by the edgeTo array.
		Digraph dg = new Digraph(dewg.verticesCount());
		for(int i = 0 ; i < edgeTo.length ; i++)
		{
			DirectedEdge e = edgeTo[i]; 
			if(e != null)
				dg.addEdge(e.from(), e.to());
		}
		
		//2. detect the cycle
		CycleDetector cd = new CycleDetector(dg);
		negativeCycle = cd.cycle();
	}

	
	public static void main(String[] args) {
		/*DirectedEdgeWeightGraph dewg = DirectedEdgeWeightGraphBuilder.FromFile("tinyEWDn.txt", " ");
		BellmanFordSP sp = new BellmanFordSP(dewg, 0);
		
		for(int i = 0 ; i < dewg.verticesCount() ; i++)
		{
			for(Integer v : sp.pathTo(i))
			{
				System.out.print(v + " => ");		
			}
			System.out.println(" Distance = " + sp.distTo(i));
			System.out.println("===============================");
		}*/
		
		DirectedEdgeWeightGraph dewgNC = DirectedEdgeWeightGraphBuilder.FromFile("tinyEWDnc.txt", " ");
		BellmanFordSP spNC = new BellmanFordSP(dewgNC, 0);
		
		if(spNC.hasNegativeCycle())
		{
			System.out.println("Has negative cycle!");
			for(int v : spNC.negativeCycle())
			{
				System.out.print(v + "=>");				
			}
		}
	}

}

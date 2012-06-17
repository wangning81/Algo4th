package mst;

import elementary.Bag;
import graphHelper.EdgeWeightGraphBuilder;
import priorityqueue.IndexMinPQ;
import undirectedgraph.Edge;
import undirectedgraph.EdgeWeightGraph;

public class PrimMST {
	private IndexMinPQ<Double> pq;
	private double[] distTo;
	private Edge[] edgeTo;
	private boolean[] marked;
	private double totalWeight;
	
	public PrimMST(EdgeWeightGraph ewg)
	{
		int vc = ewg.verticesCount();
		pq = new IndexMinPQ<Double>(vc);
		distTo = new double[vc];
		edgeTo = new Edge[vc];
		marked = new boolean[vc];
		
		for(int i = 0 ; i < distTo.length; i++)
			distTo[i] = Double.MAX_VALUE;
		
		pq.insert(0, 0.0);
		distTo[0] = 0.0;
						
		while(!pq.isEmpty())
		{
			visit(ewg, pq.delMin());
		}
	}
	
	public void visit(EdgeWeightGraph ewg, int v)
	{
		marked[v] = true;
		//distTo[v] = 0.0;
		for(Edge e : ewg.adj(v))
		{
			int u = e.other(v);
			if(!marked[u] && e.weight() < distTo[u])
			{
				if(distTo[u] == Double.MAX_VALUE) totalWeight += e.weight();
				else totalWeight -= distTo[u] - e.weight();
				
				distTo[u] = e.weight();
				edgeTo[u] = e;
				if(pq.contains(u)) pq.change(u, e.weight());
				else pq.insert(u, e.weight());				
			}
		}
	}
	
	public double weight()
	{
		return totalWeight;
	}
	
	public Iterable<Edge> edges()
	{
		Bag<Edge> ret = new Bag<Edge>();
		for(int i = 1 ; i < edgeTo.length ; i++)
			ret.add(edgeTo[i]);
		return ret;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EdgeWeightGraph ewg = EdgeWeightGraphBuilder.FromFile("tinyEWG.txt", " ");
		PrimMST mst = new PrimMST(ewg);
		for(Edge e : mst.edges())
		{
			int v = e.either(), w = e.other(v);
			System.out.printf("%s -> %s\n", v, w);
		}
	}

}

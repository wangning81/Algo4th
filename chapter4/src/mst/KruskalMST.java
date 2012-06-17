package mst;

import elementary.ArrayQueue;
import priorityqueue.MinPQ;
import undirectedgraph.Edge;
import undirectedgraph.EdgeWeightGraph;
import unionfind.WeightedQuickUnion;

public class KruskalMST {
	private MinPQ<Edge> pq = new MinPQ<Edge>();
	private ArrayQueue<Edge> mst = new ArrayQueue<Edge>();
	private double totalWeight;
	
	public KruskalMST(EdgeWeightGraph ewg)
	{
		for(Edge e : ewg.edges())
			pq.enqueue(e);
		
		WeightedQuickUnion uf = new WeightedQuickUnion(ewg.verticesCount());
		
		while(!pq.isEmpty() && mst.size() < ewg.verticesCount() - 1)
		{
			Edge e = pq.dequeue();
			int v = e.either(), u = e.other(v);
			if(!uf.connected(v, u))
			{
				mst.enqueue(e);
				totalWeight += e.weight();
				uf.union(u, v);
			}
		}
	}
	
	public double weight()
	{
		return totalWeight;
	}
	
	public Iterable<Edge> mst()
	{
		return mst;
	}
	

}

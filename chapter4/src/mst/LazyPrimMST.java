package mst;

import priorityqueue.MinPQ;
import elementary.ArrayQueue;
import undirectedgraph.Edge;
import undirectedgraph.EdgeWeightGraph;

public class LazyPrimMST{
	private ArrayQueue<Edge> mst = new ArrayQueue<Edge>();
	private MinPQ<Edge> pq = new MinPQ<Edge>();
	private boolean[] marked;
	private double totalWeight;
	
	public LazyPrimMST(EdgeWeightGraph ewg) {
		
		marked = new boolean[ewg.verticesCount()];
		
		visit(ewg, 0);
		
		while(!pq.isEmpty() && mst.size() < ewg.verticesCount() - 1)
		{
			Edge e = pq.dequeue();
			int u = e.either(), v = e.other(u);
			
			if(marked[u] && marked[v]) continue; //ignore the ineligible.
			
			this.mst.enqueue(e);
			totalWeight += e.weight();
			
			if(marked[u]) visit(ewg, v);
			else visit(ewg, u);
		}
	}
	
	private void visit(EdgeWeightGraph ewg, int v)
	{
		marked[v]  = true;
		for(Edge e : ewg.adj(v))
		{
			if(!marked[e.other(v)])
				pq.enqueue(e);
		}
	}
	
	public double weight()
	{
		return totalWeight;
	}
	
	public Iterable<Edge> edges()
	{
		return mst;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
